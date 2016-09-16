package com.console.domain;

import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * Created by exfaff on 15/09/2016.
 */
public class NodeData {

    private static final int MAX_METRICS_COUNT = 50;

    private enum NodeState {
        FINE, ANOMALY_DETECTED, FAILURE_PREDICTED
    }
    private final String node;
    private final Map<Metric, ObservableList<XYChart.Data<Object, Object>>> metrics
            = new HashMap<>();
    private NodeState state = NodeState.FINE;

    private final Map<NodeInfo.Type, NodeInfo> info = new HashMap<>();

    private NodeData(Builder builder) {
        this.node = builder.node;
        this.metrics.putAll(builder.metrics);
        this.state = builder.state;
        this.info.putAll(builder.info);
    }

    public String getNode() {
        return this.node;
    }

    public ObservableList<XYChart.Data<Object, Object>> getMetric(Metric type) {
        return this.metrics.get(type);
    }

    public void addMetricValue(Metric metric, Object key, Object value) {
        this.metrics.get(metric).add(new XYChart.Data(key, value));
    }

    public boolean AnomalyDetected() {
        return state.equals(NodeState.ANOMALY_DETECTED);
    }

    public boolean FailureDetected() {
        return state.equals(NodeState.FAILURE_PREDICTED);
    }

    public Map<NodeInfo.Type, NodeInfo> getInfo() {
        return Collections.unmodifiableMap(info);
    }

    public String toString() {
        return this.node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NodeData other = (NodeData) o;
        return Objects.equals(this.node, other.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node);
    }

    public static class Builder {

        private final String node;
        private final Map<Metric, ObservableList<XYChart.Data<Object, Object>>> metrics = new HashMap<>();
        private NodeState state = NodeState.FINE;

        private Map<NodeInfo.Type, NodeInfo> info = new HashMap<>();

        public Builder(String node) {
            this.node = node;
        }

        public Builder withMetricValue(Metric metric, Object key, Object value) {
            ObservableList<XYChart.Data<Object, Object>> serie;
            if (!metrics.containsKey(metric)) {
                serie = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
                metrics.put(metric, serie);
            } else {
                serie = metrics.get(metric);
            }
            serie.add(new XYChart.Data(key, value));
            return this;
        }

        public Builder isInAbnormalState() {
            this.state = NodeState.ANOMALY_DETECTED;
            return this;
        }

        public Builder isFailureDetected() {
            this.state = NodeState.FAILURE_PREDICTED;
            return this;
        }

        public Builder withInfo(NodeInfo info) {
            this.info.put(info.type, info);
            return this;
        }

        public NodeData build() {
            NodeData node = new NodeData(this);

            if (node.getNode().isEmpty()) {
                // thread-safe
                throw new IllegalStateException("Node id not valid");
            }

            return node;
        }

        public static void syncNewData(NodeData node, NodeData newData) {
            node.state = newData.state;
            newData.metrics.forEach((metric, newata) -> {
                ObservableList<XYChart.Data<Object, Object>> values
                        = node.metrics.get(metric);

                int total = values.size() + newata.size();
                if (total > MAX_METRICS_COUNT) {
                    // To many values, remove the oldest
                    int toDelete = total - MAX_METRICS_COUNT;
                    values.remove(0, toDelete);
                }
                values.addAll(newata);
            });
        }

    }

    public static class NodeInfo {

        public enum Type {
            IP
        };

        public NodeInfo(Type type, String value) {
            this.type = type;
            this.value = value;
        }

        private final String value;
        private final Type type;

        public String getValue() {
            return value;
        }

        public Type getType() {
            return type;
        }

    }
}
