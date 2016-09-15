package com.console.view.status;

import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.State;
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
        appService.subscribe(this);

        statusBar.textProperty().bind(appService.getCurrentState().getMessage());
        Label statusLabel = new Label();
        statusLabel.textProperty().bind(appService.getCurrentState().getStateProp());
        statusBar.getRightItems().add(new Separator(Orientation.VERTICAL));
        statusBar.getRightItems().add(statusLabel);
        statusBar.getRightItems().add(new Separator(Orientation.VERTICAL));

        setProgressIndicator(appService.getCurrentState().getState());
    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        logger.debug("Applciation state change");
        setProgressIndicator(currentState.getState());
    }

    private void setProgressIndicator(State state) {
        if (state.equals(State.NEWDATARECEIVED)
                || state.equals(State.WAITING)
                || state.equals(State.NEWDATARECEIVED)) {
            statusBar.setProgress(-1);
        } else {
            statusBar.setProgress(0);
        }
    }

}
