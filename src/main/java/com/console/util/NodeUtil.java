package com.console.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author fabry
 */
public class NodeUtil {

    public void ancorToPane(Node node, Double val) {
        AnchorPane.setBottomAnchor(node, val);
        AnchorPane.setLeftAnchor(node, val);
        AnchorPane.setRightAnchor(node, val);
        AnchorPane.setTopAnchor(node, val);
    }

    public void ancorToPaneTop(Node node, Double val) {
        AnchorPane.setTopAnchor(node, val);
    }

    public void ancorToPaneLeft(Node node, Double val) {
        AnchorPane.setLeftAnchor(node, val);
    }

    public void ancorToPaneRight(Node node, Double val) {
        AnchorPane.setRightAnchor(node, val);
    }

    public void ancorToPaneBottom(Node node, Double val) {
        AnchorPane.setBottomAnchor(node, val);
    }
}
