package com.console.domain;

/**
 *
 * @author fabry
 */
public class DataReceived {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void copy(DataReceived dataRecieved) {
        this.data = dataRecieved.getData();
    }

}
