package com.console.domain;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.*;

/**
 * Created by exfaff on 15/09/2016.
 */
public class NodeData {

    private static final int MAX_METRICS_COUNT = 200;

    private enum NodeState {FINE, ANOMALY_DETECTED, FAILURE_PREDICTED}
    private final String node;
    private final Queue<Object> cpu = new CircularFifoQueue<>(MAX_METRICS_COUNT);
    private final Queue<Object> ram  = new CircularFifoQueue<>(MAX_METRICS_COUNT);
    private Object lastCpu = 0.0;
    private Object lastRam = 0;
    private NodeState state = NodeState.FINE;

    private final Map<NodeInfo.Type, NodeInfo> info = new HashMap<>();

    private NodeData(Builder builder) {
        this.node = builder.node;
        this.cpu.addAll(builder.cpu);
        this.ram.addAll(builder.ram);
        this.state = builder.state;
        this.info.putAll(builder.info);
    }

    public String getNode() {
        return this.node;
    }

    public Collection<Object> getCpu() {
        return cpu;
    }

    public Object getLastCpu() {
        return lastCpu;
    }

    public Object getLastRam() {
        return lastRam;
    }

    public Collection<Object> getRam() {
        return ram;
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
        private final Queue<Double> cpu = new CircularFifoQueue<>(MAX_METRICS_COUNT);
        private final Queue<Long> ram  = new CircularFifoQueue<>(MAX_METRICS_COUNT);
        private NodeState state = NodeState.FINE;

        private Map<NodeInfo.Type, NodeInfo> info = new HashMap<>();

        public Builder(String node) {
            this.node = node;
        }

        public Builder withCpuMetric(Double cpu) {
            this.cpu.add(cpu);
            return this;
        }

        public Builder withRamMetric(Long ram) {
            this.ram.add(ram);
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
            node.cpu.clear();
            node.cpu.addAll(newData.cpu);
            Iterator<Object> it = newData.cpu.iterator();
            while(it.hasNext()) {
                node.lastCpu = it.next();
            }
            node.ram.clear();
            node.ram.addAll(newData.ram);
            it = newData.ram.iterator();
            while(it.hasNext()) {
                node.lastRam = it.next();
            }
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
