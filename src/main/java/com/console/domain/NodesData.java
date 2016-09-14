package com.console.domain;

import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

/**
 *
 * @author fabry
 */
public class NodesData {

    private final ObservableSet<NodeData> nodes = FXCollections.observableSet();

    public NodesData() {
    }

    public ObservableSet<NodeData> getNodes() {
        return nodes;
    }

    public void addNodeData(NodeData node) {
        nodes.add(node);
    }
}
