package com.console.view.status;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author user
 */
public class StatusPresenter  implements Initializable {

    @FXML
    private Label statusLabel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusLabel.setText("Init");
    }
    
}
