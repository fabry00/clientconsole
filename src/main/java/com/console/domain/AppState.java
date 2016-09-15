package com.console.domain;

import java.util.Objects;
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
    private final NodesData dataRecieved = new NodesData();
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
    
    public NodesData getDataReceived() {
        return dataRecieved;
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
        this.dataRecieved.addNodeData(newNodeData);
    }

    public void addAbnormalNode(NodeData node) {
        int index = nodesInAnomalySate.lastIndexOf(node);
        if(index < 0) {
            nodesInAnomalySate.add(node);
        }
    }
    
    public AppState clone() {
        AppState cloned = new AppState();
        
        cloned.setState(state);
        this.dataRecieved.getNodes().stream().forEach((node) -> cloned.addNodeData(node));
        cloned.setMessage(message.getValue());
        
        return cloned;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AppState other = (AppState) o;
        return Objects.equals(getState(), other.getState())
                && Objects.equals(getMessage(), other.getMessage())
                && Objects.equals(getDataReceived(), other.getDataReceived());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getState())
                + Objects.hash(getMessage())
                + Objects.hash(getDataReceived());
    }

}
