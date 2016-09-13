package com.console.domain;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author fabry
 */
public class AppState {
    
    private State state;
    private final StringProperty stateProperty = new SimpleStringProperty("");
    private final StringProperty message = new SimpleStringProperty("");
    private final DataReceived dataRecieved = new DataReceived();
    
    public State getState() {
        return state;
    }
    
    public StringProperty getStateProp() {
        return stateProperty;
    }
    
    public StringProperty getMessage() {
        return message;
    }
    
    public DataReceived getDataReceived() {
        return dataRecieved;
    }
    
    public void setState(State state) {
        this.state = state;
        this.stateProperty.set(state.getLabel());
    }
    
    public void setMessage(String message) {
        this.message.set(message);
    }
    
    public void setDataReceived(DataReceived dataRecieved) {
        this.dataRecieved.copy(dataRecieved);
    }
    
    public AppState clone() {
        AppState cloned = new AppState();
        
        cloned.setState(state);
        cloned.setDataReceived(dataRecieved);
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
