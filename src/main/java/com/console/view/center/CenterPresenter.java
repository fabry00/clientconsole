/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.view.center;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.service.appservice.ApplicationService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.inject.Inject;

/**
 *
 * @author fabry
 */
public class CenterPresenter implements Initializable {

    @Inject
    private ApplicationService appService;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

      
    }

    @FXML
    public void handleStart() {
        appService.dispatch(new Action<>(ActionType.START, null));
    }

    @FXML
    public void handleStop() {
        appService.dispatch(new Action<>(ActionType.STOP, null));
    }

}
