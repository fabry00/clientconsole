package com.console.view.dashboard;

import com.console.domain.Action;
import com.console.service.appservice.ApplicationService;
import com.console.domain.ActionType;
import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.ServiceName;
import com.console.service.backend.IBackendService;
import com.console.view.light.LightView;
import com.console.view.status.StatusView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author adam-bien.com
 */
public class DashboardPresenter implements Initializable, IAppStateListener {
    
    private final Logger logger = Logger.getLogger(DashboardPresenter.class);

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
    private ApplicationService appService;
    
    @Inject
    private IBackendService backendService;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.debug("Initialize");
        injectServices();
        appService.subscribe(this);
        //fetched from followme.properties
        //this.theVeryEnd = rb.getString("theEnd");        
        bottomPane.getChildren().add(new StatusView().getView());
        
        initApp();
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

    /**
     * Closes the application.
     */
    @FXML
    public void handleExit() {
        appService.dispatch(new Action<>(ActionType.CLOSE, null));        
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
        logger.debug("AppStateChanged");
    }
    
    
    private void initApp() {
        logger.debug("initApp");
        appService.dispatch(new Action<>(ActionType.START, null));
    }

    private void injectServices() {
       
        Map<ServiceName, Object> services = new HashMap<>();
        services.put(ServiceName.BACKEND, backendService);
                
        appService.injectServices(services);
    }

    

    
}
