package com.console.view.dashboard;

import com.console.domain.*;
import com.console.service.appservice.ApplicationService;
import com.console.util.NodeUtil;
import com.console.view.center.CenterView;
import com.console.view.logo.LogoView;
import com.console.view.status.StatusView;
import com.console.view.tree.TreeView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.controlsfx.control.NotificationPane;

/**
 *
 * @author adam-bien.com
 */
public class DashboardPresenter implements Initializable {

    private final Logger logger = Logger.getLogger(DashboardPresenter.class);

    @FXML
    private Pane bottomPane;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private AnchorPane leftPane;

    @Inject
    private ApplicationService appService;

    private NotificationPane notificationPane;

    private NodeUtil util = new NodeUtil();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.debug("Initialize");

        //fetched from followme.properties
        //logger.error(rb.getString("theEnd"));
        setCenterPane();
        setLeftPane();
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
        appService.dispatch(new Action<>(ActionType.CHANGE_THEME, null));
    }

    @FXML
    public void handleFullScreen() {
        appService.dispatch(new Action<>(ActionType.FULL_SCREEN, true));
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

        util.ancorToPane(statusView, 0.0);
        bottomPane.getChildren().add(statusView);
    }

    private void setCenterPane() {

        /*notificationPane = new NotificationPane();
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
        centerPane.getChildren().add(notificationPane);*/
        BorderPane center = (BorderPane) new CenterView().getView();
        util.ancorToPane(center, 0.0);
        centerPane.getChildren().add(center);
    }

    private void setLeftPane() {
        AnchorPane logo = (AnchorPane) new LogoView().getView();
        util.ancorToPaneLeft(logo, 0.0);
        util.ancorToPaneRight(logo, 0.0);
        util.ancorToPaneTop(logo, 0.0);
        logo.setPadding(new Insets(0));
        leftPane.getChildren().add(logo);

        AnchorPane tree = (AnchorPane) new TreeView().getView();
        util.ancorToPaneTop(tree, logo.getPrefHeight());
        util.ancorToPaneLeft(tree, 0.0);
        util.ancorToPaneRight(tree, 0.0);
        util.ancorToPaneBottom(tree, 0.0);
        tree.setPadding(new Insets(0));
        leftPane.getChildren().add(tree);
    }

}
