package com.console.view.status;

import com.console.service.ApplicationService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author user
 */
public class StatusPresenter implements Initializable {

    private final Logger logger = Logger.getLogger(StatusPresenter.class);

    @FXML
    private Label statusLabel;

    @Inject
    private ApplicationService appService;

    private final StringProperty statusLabelText = new SimpleStringProperty("");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusLabelText.set(storeService.state().status().getLabel());

        statusLabel.textProperty().bind(statusLabelText);

        storeService.subscribe(() -> {
            statusLabelText.set(storeService.state().status().getLabel());
            logger.debug("event fired --> set status label " + statusLabelText.get());
        });
    }

}
