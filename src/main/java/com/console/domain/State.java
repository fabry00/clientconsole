package com.console.domain;

/**
 *
 * @author Fabrizio Faustinoni
 */
public enum State {

    UNKWOWN("Unknown"),
    NOT_STARTED(""),
    STARTING("Starting"),
    STARTED("Started"),
    STOPPED("Stopped"),
    WAITING("Waiting"),
    PROCESSING("Processing"), 
    CLOSING("Closing");
    
    
    private final String label;
    private State(String label) {
        this.label = label;
    }
    
    public String getLabel(){
        return this.label;
    }
    
}
