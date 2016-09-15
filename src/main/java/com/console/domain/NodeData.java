package com.console.domain;

/**
 *
 * @author fabry
 */
public class NodeData {
    
    private final String data;
    private final String node;

    public NodeData(String node, String data) {
        this.data = data;
        this.node = node;
    }

    public String getData() {
        return data;
    }

    public String getNode() {
        return node;
    }

}
