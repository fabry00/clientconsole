package com.console.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author fabry
 */
public class Node {

    private final ObservableList<NodeData> nodesSync
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    public Node() {
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
