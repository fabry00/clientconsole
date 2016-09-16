package com.console.domain;

import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

/**
 *
 * @author fabry
 */
public class NodesData {

    private final ObservableList<NodeData> nodesSync
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    public NodesData() {
    }

    public ObservableList<NodeData> getNodes() {
        return nodesSync;
    }

    public void addNodeData(NodeData node) {

        int itemIndex = nodesSync.lastIndexOf(node);
        NodeData nodeData;
        if (itemIndex >= 0) {
            nodeData = nodesSync.get(itemIndex);
            //nodesSync.remove(itemIndex);
            //nodesSync.add(itemIndex, node);
            //NodeData currentNode = nodesSync.get(itemIndex);
            NodeData.Builder.syncNewData(nodeData, node);
        } else {
            nodeData = node;
            nodesSync.add(node);
        }
    }

}
