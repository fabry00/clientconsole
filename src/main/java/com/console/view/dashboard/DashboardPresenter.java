package com.console.view.dashboard;

import com.console.service.Tower;
import com.console.view.light.LightView;
import com.console.view.status.StatusView;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
        System.exit(0);
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
