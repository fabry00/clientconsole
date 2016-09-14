package com.console.view.dashboard;

import com.console.domain.Action;
import com.console.service.appservice.ApplicationService;
import com.console.domain.ActionType;
import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.util.NodeUtil;
import com.console.view.center.CenterView;
import com.console.view.status.StatusView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author adam-bien.com
 */
public class DashboardPresenter implements Initializable, IAppStateListener {

    private final Logger logger = Logger.getLogger(DashboardPresenter.class);

    @FXML
    private Pane bottomPane;

    @FXML
    private AnchorPane centerPane;

    @Inject
    private ApplicationService appService;

    private NodeUtil util = new NodeUtil();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.debug("Initialize");
        appService.subscribe(this);
        //fetched from followme.properties
        logger.error(rb.getString("theEnd"));

        setCenterPane();
        setBottomPane();

        initApp();
    }

    public void createLights() {
        /*  for (int i = 0; i < 255; i++) {
            final int red = i;
            LightView view = new LightView((f) -> red);
             view.getViewAsync(lightsBox.getChildren()::add);
        }
         StatusView view = new StatusView();
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
    public void handleChangeTheme() {
        appService.dispatch(new Action<>(ActionType.CHANGE_THEME,null));
    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        logger.debug("AppStateChanged");
    }

    public ApplicationService getAppService() {
        return appService;
    }

    private void initApp() {
        logger.debug("initApp");
        appService.dispatch(new Action<>(ActionType.START, null));
    }

    private void setBottomPane() {
        StatusView view = new StatusView();
        AnchorPane statusView = (AnchorPane) view.getView();

        util.ancorToPane(statusView);
        bottomPane.getChildren().add(statusView);
    }

    private void setCenterPane() {
        BorderPane center = (BorderPane) new CenterView().getView();
        util.ancorToPane(center);
        centerPane.getChildren().add(center);
    }

}
