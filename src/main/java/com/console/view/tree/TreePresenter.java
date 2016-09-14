package com.console.view.tree;

import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.NodeData;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class TreePresenter implements Initializable, IAppStateListener {

    private Logger logger = Logger.getLogger(TreePresenter.class);

    @Inject
    ApplicationService appService;

    @FXML
    private ListView nodeList;
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("initialize");
        
        appService.subscribeToStae(this, State.NEWDATARECEIVED);
        ListProperty<NodeData> listProperty = new SimpleListProperty<>();
        listProperty.addAll(appService.getCurrentState().getDataReceived().getNodes());
        nodeList.itemsProperty().bind(listProperty);
    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        logger.debug("New Data received "+nodeList.getItems().size());
    }

}
