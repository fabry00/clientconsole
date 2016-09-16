package com.console.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author fabry
 */
public class AppState {

    private State state;
    private final StringProperty stateProperty = new SimpleStringProperty("");
    private final StringProperty message = new SimpleStringProperty("");
    private final ObservableList<NodeData> nodes
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    private final ObservableList<NodeData> nodesInAnomalySate
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    public State getState() {
        return state;
    }

    public StringProperty getStateProp() {
        return stateProperty;
    }

    public StringProperty getMessage() {
        return message;
    }

    public ObservableList getNodesInAbnormalState() {
        return nodesInAnomalySate;
    }

    public void setState(State state) {
        this.state = state;
        this.stateProperty.set(state.getLabel());
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public void addNodeData(NodeData newNodeData) {
        int itemIndex = nodes.lastIndexOf(newNodeData);
        NodeData nodeData;
        if (itemIndex >= 0) {
            nodeData = nodes.get(itemIndex);
            //nodesSync.remove(itemIndex);
            //nodesSync.add(itemIndex, node);
            //NodeData currentNode = nodesSync.get(itemIndex);
            NodeData.Builder.syncNewData(nodeData, newNodeData);
        } else {
            nodes.add(newNodeData);
        }
    }

    public void addAbnormalNode(NodeData node) {
        int index = nodesInAnomalySate.lastIndexOf(node);
        if (index < 0) {
            nodesInAnomalySate.add(node);
        }
    }

    public AppState clone() {
        AppState cloned = new AppState();

        cloned.setState(state);
        nodes.stream().forEach((node) -> cloned.addNodeData(node));
        cloned.setMessage(message.getValue());

        return cloned;
    }

    public ObservableList<NodeData> getNodes() {
        return nodes;
    }
}
