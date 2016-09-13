package com.console.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author fabry
 */
public class NodeUtil {

    public void ancorToPane(Node node) {
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
    }
}
