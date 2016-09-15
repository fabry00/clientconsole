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
        if(itemIndex >= 0 ) {
            nodesSync.remove(itemIndex);
            nodesSync.add(itemIndex,node);
        } else {
            nodesSync.add(node);
        }
    }

}
