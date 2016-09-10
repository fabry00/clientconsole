package com.console.view.status;

import com.console.service.StatusService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.inject.Inject;

/**
 *
 * @author user
 */
public class StatusPresenter  implements Initializable {

    @FXML
    private Label statusLabel;
    
    @Inject
    StatusService statusService;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StringProperty currentStatus = 
                new SimpleStringProperty(statusService.getCurrentStatus().getLabel());
        statusLabel.textProperty().bind(currentStatus);
    }
    
}
