package com.console.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by exfaff on 15/09/2016.
 */
public class NodeData {

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

    };
    private final String node;
    private final Double cpu;
    private final Long ram;
    private final boolean anomalyDetected;
    private final boolean failureDetected;
    private final Map<NodeInfo.Type, NodeInfo> info = new HashMap<>();

    private NodeData(Builder builder) {
        this.node = builder.node;
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.anomalyDetected = builder.anomalyDetected;
        this.failureDetected = builder.failureDetected;
        this.info.putAll(builder.info);
    }

    public String getNode() {
        return this.node;
    }

    public Double getCpu() {
        return cpu;
    }

    public Long getRam() {
        return ram;
    }

    public boolean AnomalyDetected() {
        return anomalyDetected;
    }

    public boolean FailureDetected() {
        return failureDetected;
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
        private Double cpu = 0.0;
        private Long ram = new Long(0);
        private boolean anomalyDetected = false;
        private boolean failureDetected = false;
        private Map<NodeInfo.Type, NodeInfo> info = new HashMap<>();

        public Builder(String node) {
            this.node = node;
        }

        public Builder withCpuMetric(Double cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder withRamMetric(Long ram) {
            this.ram = ram;
            return this;
        }

        public Builder isInAbnormalState() {
            this.anomalyDetected = true;
            return this;
        }

        public Builder isFailureDetected() {
            this.failureDetected = true;
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
    }
}
