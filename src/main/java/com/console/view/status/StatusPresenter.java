package com.console.view.status;

import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.service.appservice.ApplicationService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.controlsfx.control.StatusBar;

/**
 *
 * @author user
 */
public class StatusPresenter implements Initializable, IAppStateListener {

    private final Logger logger = Logger.getLogger(StatusPresenter.class);

    @Inject
    private ApplicationService appService;

    @FXML
    private StatusBar statusBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        //appService.subscribe(this);

        //statusLabelText.set(appService.getCurrentState().getState().getLabel());
        //systemMessageText.set("");
        //statusLabel.textProperty().bind(statusLabelText);
        //systemMessage.textProperty().bind(systemMessageText);
        statusBar.textProperty().bind(appService.getCurrentState().getMessage());
        Label statusLabel = new Label();
        statusLabel.textProperty().bind(appService.getCurrentState().getStateProp());
        Separator separator = new Separator(Orientation.VERTICAL);
        statusBar.getRightItems().add(statusLabel);
        statusBar.getRightItems().add(separator);

        /*storeService.subscribe(() -> {
            statusLabelText.set(storeService.state().status().getLabel());
            logger.debug("event fired --> set status label " + statusLabelText.get());
        });  */
    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        logger.debug("Applciation state change");
        /*statusLabelText.set(currentState.getState().getLabel());
        statusBallon.setFill(currentState.getState().getColor());
        if (appService.getCurrentState().getMessage().isPresent()) {
            systemMessageText.set(appService.getCurrentState().getMessage().get());
        }*/
    }

}
