package com.console.view.status;

import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.service.appservice.ApplicationService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author user
 */
public class StatusPresenter implements Initializable, IAppStateListener {

    private final Logger logger = Logger.getLogger(StatusPresenter.class);

    @FXML
    private Label statusLabel;

    @FXML
    private Circle statusBallon;

    @FXML
    private Label systemMessage;

    @Inject
    private ApplicationService appService;

    private final StringProperty statusLabelText = new SimpleStringProperty("");
    private final StringProperty systemMessageText = new SimpleStringProperty("");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        appService.subscribe(this);
        statusLabelText.set(appService.getCurrentState().getState().getLabel());
        systemMessageText.set("");
        statusLabel.textProperty().bind(statusLabelText);
        systemMessage.textProperty().bind(systemMessageText);

        /*storeService.subscribe(() -> {
            statusLabelText.set(storeService.state().status().getLabel());
            logger.debug("event fired --> set status label " + statusLabelText.get());
        });  */
    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        logger.debug("Applciation state change");
        statusLabelText.set(currentState.getState().getLabel());
        statusBallon.setFill(currentState.getState().getColor());
        if (appService.getCurrentState().getMessage().isPresent()) {
            systemMessageText.set(appService.getCurrentState().getMessage().get());
        }
    }

}
