package com.console.view.center;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import com.console.util.NodeUtil;
import com.console.view.graphdata.GraphdataView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class CenterPresenter implements Initializable, IAppStateListener {

    private final Logger logger = Logger.getLogger(CenterPresenter.class);

    @Inject
    private ApplicationService appService;

    @FXML
    Button startButton;

    @FXML
    Button stopButton;

    @FXML
    AnchorPane centerPane;

    private SimpleBooleanProperty startDisabled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("initialize");
        startDisabled = new SimpleBooleanProperty();
        setStartDisabled(appService.getCurrentState().getState());

        startButton.disableProperty().bind(startDisabled);
        stopButton.disableProperty().bind(startDisabled.not());

        NodeUtil util = new NodeUtil();
        AnchorPane graphPane = (AnchorPane) new GraphdataView().getView();
        util.ancorToPane(graphPane, 0.0);
        centerPane.getChildren().add(graphPane);

        appService.subscribe(this);

    }

    @FXML

    public void handleStart() {
        appService.dispatch(new Action<>(ActionType.START, null));
    }

    @FXML
    public void handleStop() {
        appService.dispatch(new Action<>(ActionType.STOP, null));
    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        setStartDisabled(currentState.getState());
    }

    private void setStartDisabled(State state) {
        boolean disabled = state.equals(State.STARTED)
                || state.equals(State.NEWDATARECEIVED)
                || state.equals(State.ABNORMAL_NODE_STATE);
        logger.debug("Set setStartDisabled " + disabled + " state: " + state.toString());

        startDisabled.set(disabled);
    }

}
