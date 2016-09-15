package com.console.view.graphdata.toolbar;

import com.console.domain.Metric;
import com.console.domain.NodeData;
import com.console.service.appservice.ApplicationService;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javax.inject.Inject;
import org.controlsfx.control.CheckComboBox;

/**
 *
 * @author fabry
 */
public class ToolbarPresenter implements Initializable {

    private final ObservableList<NodeData> nodesInComboBox = FXCollections.observableArrayList();
    @FXML
    private ToolBar graphToolbar;

    private CheckComboBox<NodeData> graphNodeChooser;

    private ComboBox<String> metricSelector;

    @Inject
    ApplicationService appService;

    private final Set<IToolbarListener> listeners = new HashSet<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Label label = new Label("Nodes: ");
        graphToolbar.getItems().add(label);

        // Do no use this, otherwise you lose the selection
        //graphNodeChooser = new CheckComboBox<>(appService.getCurrentState().getDataReceived().getNodes());
        graphNodeChooser = new CheckComboBox<>(nodesInComboBox);

        graphNodeChooser.setPrefWidth(200);
        graphToolbar.getItems().add(graphNodeChooser);

        Button buttonAddAll = new Button("Add All");
        graphToolbar.getItems().add(buttonAddAll);

        Button buttonRemoveAll = new Button("Remove All");
        graphToolbar.getItems().add(buttonRemoveAll);

        Button buttonReset = new Button("Reset");
        graphToolbar.getItems().add(buttonReset);

        Separator separator = new Separator(Orientation.VERTICAL);
        double padding = 15;
        separator.setPadding(new Insets(0, padding, 0, padding));
        graphToolbar.getItems().add(separator);

        ObservableList<String> options
                = FXCollections.observableArrayList(getMetricsList(Metric.class));

        metricSelector = new ComboBox<>();
        metricSelector.getItems().setAll(options);
        metricSelector.setPromptText("Metric: ");
        graphToolbar.getItems().add(metricSelector);

        metricSelector.valueProperty()
                .addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
                    metricSelected();
                });

        metricSelector.getSelectionModel().select(0);

        buttonReset.setOnAction(e -> resetSeries());
        buttonAddAll.setOnAction(e -> addAll());
        buttonRemoveAll.setOnAction(e -> {
            removeAll();
            resetSeries();
        });
    }

    public void subscribe(IToolbarListener listener) {
        this.listeners.add(listener);
    }

    public String[] getMetricsList(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public void addItem(NodeData node) {
        if (!nodesInComboBox.contains(node)) {
            nodesInComboBox.add(node);
        }
    }

    public boolean isChecked(NodeData node) {
        return graphNodeChooser.checkModelProperty().get().isChecked(node);
    }

    public Metric getSelectedMetric() {
        return Metric.valueOf(metricSelector.getSelectionModel().getSelectedItem());
    }

    private void resetSeries() {
        listeners.forEach((listener) -> {
            Platform.runLater(() -> listener.resetSeriesClicked());
        });
    }

    private void addAll() {
        graphNodeChooser.checkModelProperty().get().checkAll();
        listeners.forEach((listener) -> {
            Platform.runLater(() -> listener.addAllClicked());
        });
    }

    private void removeAll() {
        graphNodeChooser.checkModelProperty().get().clearChecks();
        listeners.forEach((listener) -> {
            Platform.runLater(() -> listener.removeAllClicked());
        });

    }

    private void metricSelected() {
        Metric metric = Metric.valueOf(metricSelector.getSelectionModel().getSelectedItem());
        listeners.forEach((listener) -> {
            Platform.runLater(() -> listener.metricSelected(metric));
        });

    }

}
