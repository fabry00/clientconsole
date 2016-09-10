package com.console.view.dashboard;

import com.console.domain.ActionType;
import com.console.domain.Status;
import com.console.service.StoreService;
import com.console.service.backend.CommandLineAppService;
import com.console.service.backend.IBackendService;
import com.console.view.light.LightView;
import com.console.view.status.StatusView;
import com.jedux.Action;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author adam-bien.com
 */
public class DashboardPresenter implements Initializable {

    /*@FXML
    Label message;

    @FXML
    Pane lightsBox;

    @Inject
    Tower tower;

    //! This come from configuration.properties
    @Inject
    private String prefix;

    @Inject
    private String happyEnding;

    @Inject
    private LocalDate date;

    private String theVeryEnd;*/
    
    @FXML
    private Pane bottomPane;
    
    @Inject
    private StoreService store;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //fetched from followme.properties
      //this.theVeryEnd = rb.getString("theEnd");
      
      bottomPane.getChildren().add(new StatusView().getView());
    }
    
     /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        store.dispatch(new Action<>(ActionType.CLOSE, null));
    }
    
    @FXML 
    private void handleTest() {
        store.dispatch(new Action<>(ActionType.START, null));
    }
    
    @FXML 
    private void handleStop() {
        store.dispatch(new Action<>(ActionType.STOP, null));
    }

    @PostConstruct
    public void init() {
        
    }
    
    public void createLights() {
        for (int i = 0; i < 255; i++) {
            final int red = i;
            LightView view = new LightView((f) -> red);
     //       view.getViewAsync(lightsBox.getChildren()::add);
        }
       /* StatusView view = new StatusView();
        lightsBox.getChildren().add(view.getView());*/
    }

    public void launch() {
      //  message.setText("Date: " + date + " -> " + prefix + tower.readyToTakeoff() + happyEnding + theVeryEnd
      //  );
    }

}
