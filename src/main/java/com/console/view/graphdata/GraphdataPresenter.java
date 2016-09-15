package com.console.view.graphdata;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.NodeData;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javafx.collections.FXCollections;
import org.controlsfx.control.CheckComboBox;

/**
 *
 * @author fabry
 */
public class GraphdataPresenter implements Initializable, IAppStateListener {

    private final Logger logger = Logger.getLogger(GraphdataPresenter.class);

    private enum Metric {
        CPU("Cpu Monitoring (in %)"),
        MEMORY("Memory Monitoring (in Kb)"),
        SPACE("Space Monitoring (in KB)"),
        NETWORK("Network Monitoring (in Kb/s)");

        private String title;
        Metric(String title) {
            this.title = title;
        }
        public String getTitle() {return this.title;}

    }

    //http://fxexperience.com/2012/01/curve-fitting-and-styling-areachart/
    @FXML
    private AreaChart chart;

    @FXML
    private ToolBar graphToolbar;

    private CheckComboBox<NodeData> graphNodeChooser;

    private ComboBox<String> metricSelector;

    private ObservableList<NodeData> nodesInComboBox = FXCollections.observableArrayList();

    @Inject
    ApplicationService appService;


    private static  final int MAX_ELEMENTS = 60;

    private Map<NodeData,XYChart.Series> series = new ConcurrentHashMap<>();

    private ObservableList<XYChart.Series<Integer, Integer>> seriesList
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    private   ObservableList<String> options;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        chart.setTitle("");

        appService.subscribe(this);

        chart.setLegendSide(Side.LEFT);
        chart.setLegendVisible(true);
        chart.setScaleX(1);

        // Force if to show always the zero element
        ((NumberAxis)chart.getXAxis()).setForceZeroInRange(false);
        chart.setData(seriesList);

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


        ObservableList<String> options =
                FXCollections.observableArrayList(getMetricsList(Metric.class));
        //options.addAll(Metric.values());*/
        metricSelector = new ComboBox<>();
        metricSelector.getItems().setAll(options);
        metricSelector.setPromptText("Metric: ");
        graphToolbar.getItems().add(metricSelector);


        metricSelector.valueProperty().addListener((ov, t, t1) -> {
            resetSeries();
            chart.setTitle(Metric.valueOf(metricSelector.getSelectionModel().getSelectedItem()).getTitle());
        });
        metricSelector.getSelectionModel().select(0);

        buttonReset.setOnAction(e -> resetSeries());
        buttonAddAll.setOnAction(e -> addAll());
        buttonRemoveAll.setOnAction(e -> {removeAll();resetSeries();});
    }

    public String[] getMetricsList(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {

        if(!currentState.getState().equals(State.NEWDATARECEIVED) &&
                !currentState.getState().equals(State.ABNORMAL_NODE_STATE)) {
            return;
        }
        // If we use parallelStream we have Not on FX application thread Exception
        currentState.getDataReceived().getNodes().forEach((node) -> {

            if(!nodesInComboBox.contains(node)) {
                nodesInComboBox.add(node);
            }

            if(!graphNodeChooser.checkModelProperty().get().isChecked(node)) {
                if(series.containsKey(node)) {
                    // delete series
                    seriesList.remove(series.get(node));
                    series.remove(node);
                }
                return;
            }
            XYChart.Series nodeSerie;
            if(!series.containsKey(node)) {
                // Add series
                nodeSerie = new XYChart.Series();
                nodeSerie.setName(node.getNode() + " "+ metricSelector.getSelectionModel().getSelectedItem());
                series.put(node,nodeSerie);
                seriesList.add(nodeSerie);
            } else {
                nodeSerie = series.get(node);
            }

            int currentX = nodeSerie.getData().size();
            if(currentX > MAX_ELEMENTS) {
                // Reached the limit. Delete the first element
                nodeSerie.getData().remove(0);
            }

            Object value = getValue(node);
            if(value != null) {
                nodeSerie.getData().add(new XYChart.Data(currentX, value));
            } else {
                logger.error("Error getting value");
            }
        });
    }

    private Object getValue(NodeData node) {
        Metric metric = Metric.valueOf(metricSelector.getSelectionModel().getSelectedItem());
        switch ( metric) {
            case CPU: {
                return node.getCpu();
            }
            case MEMORY: {
                return node.getRam();
            }
            default: {
                logger.error("Unknown metric: "+metricSelector.getSelectionModel().getSelectedItem());
            }
        }
        return null;
    }

    private void resetSeries() {
        seriesList.forEach((serie) -> serie.getData().clear());
        chart.requestLayout();
    }

    private void addAll() {
        graphNodeChooser.checkModelProperty().get().checkAll();
    }

    private void removeAll() {
        graphNodeChooser.checkModelProperty().get().clearChecks();
    }
}
