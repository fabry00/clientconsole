package com.console.domain;

import java.util.Objects;

/**
 * Created by exfaff on 15/09/2016.
 */
public class NodeData {

    private final String node;
    private final Double cpu;
    private final Long ram;
    public boolean anomalyDetected;

    private NodeData(Builder builder) {
        this.node = builder.node;
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.anomalyDetected = builder.anomalyDetected;
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
