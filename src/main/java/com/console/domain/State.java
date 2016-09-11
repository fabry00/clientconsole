package com.console.domain;

import javafx.scene.paint.Color;

/**
 *
 * @author Fabrizio Faustinoni
 */
public enum State {

    UNKWOWN("Unknown",Color.GRAY),
    NOT_STARTED("",Color.GRAY),
    STARTING("Starting",Color.YELLOWGREEN),
    STARTED("Started",Color.GREEN),
    STOPPED("Stopped",Color.GRAY),
    WAITING("Waiting",Color.YELLOW),
    NEWDATARECEIVED("Receiving",Color.GREEN),
    CLOSING("Closing",Color.YELLOW),
    ERROR("Error",Color.GRAY);
    
    
    private final String label;
    private final Color color;

    private State(String label, Color color) {
        this.label = label;
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    public String getLabel(){
        return this.label;
    }
    
}
