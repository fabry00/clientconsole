/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.view.center;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    private SimpleBooleanProperty startDisabled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("initialize");
        startDisabled = new SimpleBooleanProperty();
        setStartDisabled(appService.getCurrentState().getState());

        startButton.disableProperty().bind(startDisabled);
        stopButton.disableProperty().bind(startDisabled.not());

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
        boolean disabled = state.equals(State.STARTED) ||
                state.equals(State.NEWDATARECEIVED);
        logger.debug("Set setStartDisabled "+disabled+" state: "+state.toString());
        
        startDisabled.set(disabled);
    }

}
