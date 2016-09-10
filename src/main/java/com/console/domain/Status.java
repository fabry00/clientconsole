package com.console.domain;

/**
 *
 * @author Fabrizio Faustinoni
 */
public enum Status {

    UNKWOWN("Unknown"),
    INIT("Init"),
    STARTING("Starting"),
    WAITING("Waiting"),
    PROCESSING("Processing");
    
    private final String label;
    private Status(String label) {
        this.label = label;
    }
    
    public String getLabel(){
        return this.label;
    }
    
}
