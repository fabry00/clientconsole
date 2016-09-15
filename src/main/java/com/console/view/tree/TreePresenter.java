package com.console.view.tree;

import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.NodeData;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import java.net.URL;
import java.util.*;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javax.inject.Inject;

import javafx.util.Callback;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class TreePresenter implements Initializable {

    private Logger logger = Logger.getLogger(TreePresenter.class);

    @Inject
    ApplicationService appService;

    @FXML
    private ListView nodeList;

    protected ListProperty<NodeData> listProperty = new SimpleListProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("initialize");
        listProperty.set(appService.getCurrentState().getDataReceived().getNodes());
        nodeList.itemsProperty().bind(listProperty);

        nodeList.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new DefaultListCell();
            }
        });

    }

    private static class DefaultListCell<T> extends ListCell<T> {
        @Override public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else if (item instanceof NodeData) {
                NodeData node = (NodeData)item;
                setText(node.getNode());
                if(node.AnomalyDetected()) {
                    setStyle("-fx-background-color: red;");
                }else {
                    setStyle("");
                }


            } else {
                setText(item == null ? "null" : item.toString());
                setGraphic(null);
            }
        }
    }
}
