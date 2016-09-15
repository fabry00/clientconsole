package com.console.view.graphdata;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.Metric;
import com.console.domain.NodeData;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import com.console.util.NodeUtil;
import com.console.view.graphdata.toolbar.IToolbarListener;
import com.console.view.graphdata.toolbar.ToolbarPresenter;
import com.console.view.graphdata.toolbar.ToolbarView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javafx.collections.FXCollections;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author fabry
 */
public class GraphdataPresenter implements Initializable, IAppStateListener, IToolbarListener {
    
    private static final int MAX_ELEMENTS = 60;
    private final Logger logger = Logger.getLogger(GraphdataPresenter.class);
    
    private final Map<NodeData, XYChart.Series> series = new ConcurrentHashMap<>();
    
    private final ObservableList<XYChart.Series<Integer, Integer>> seriesList
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    //http://fxexperience.com/2012/01/curve-fitting-and-styling-areachart/
    @FXML
    private AreaChart chart;
    
    @FXML
    private AnchorPane graphToolbarPane;
    
    @Inject
    ApplicationService appService;
    
    private ToolbarPresenter tbPresenter;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");
        
        appService.subscribe(this);
        
        initToolbar();
        initChart();
        
    }
    
    @Override
    public void addAllClicked() {
        resetSeriesClicked();
    }
    
    @Override
    public void removeAllClicked() {
    }
    
    @Override
    public void metricSelected(Metric metric) {
        resetSeriesClicked();
        chart.setTitle(metric.getTitle());
    }
    
    @Override
    public void resetSeriesClicked() {
        seriesList.forEach((serie) -> serie.getData().clear());
        chart.requestLayout();
    }
    
    private void initToolbar() {
        ToolbarView tbView = new ToolbarView();
        AnchorPane tbPane = (AnchorPane) tbView.getView();
        graphToolbarPane.getChildren().add(tbPane);
        NodeUtil util = new NodeUtil();
        util.ancorToPaneLeft(tbPane, 0.0);
        util.ancorToPaneTop(tbPane, 0.0);
        util.ancorToPaneRight(tbPane, 0.0);
        tbPresenter = tbView.getRealPresenter();
        tbPresenter.subscribe(this);
    }
    
    private void initChart() {
        chart.setLegendSide(Side.LEFT);
        chart.setLegendVisible(true);
        chart.setScaleX(1);

        // Force if to show always the zero element
        ((NumberAxis) chart.getXAxis()).setForceZeroInRange(false);
        chart.setData(seriesList);
        
        chart.setTitle(tbPresenter.getSelectedMetric().getTitle());
    }
    
    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        
        if (!currentState.getState().equals(State.NEWDATARECEIVED)
                && !currentState.getState().equals(State.ABNORMAL_NODE_STATE)) {
            return;
        }
        // If we use parallelStream we have Not on FX application thread Exception
        currentState.getDataReceived().getNodes().forEach((node) -> {

            // Add note to toolbar if not exists
            tbPresenter.addItem(node);
            
            if (!tbPresenter.isChecked(node)) {
                if (series.containsKey(node)) {
                    // delete series
                    seriesList.remove(series.get(node));
                    series.remove(node);
                }
                return;
            }
            XYChart.Series nodeSerie;
            if (!series.containsKey(node)) {
                // Add series
                nodeSerie = new XYChart.Series();
                nodeSerie.setName(node.getNode() + " " + tbPresenter.getSelectedMetric());
                series.put(node, nodeSerie);
                seriesList.add(nodeSerie);
            } else {
                nodeSerie = series.get(node);
            }
            
            int currentX = nodeSerie.getData().size();
            if (currentX > MAX_ELEMENTS) {
                // Reached the limit. Delete the first element
                nodeSerie.getData().remove(0);
            }
            
            Object value = getValue(node);
            if (value != null) {
                nodeSerie.getData().add(new XYChart.Data(currentX, value));
            } else {
                logger.error("Error getting value");
            }
        });
    }
    
    private Object getValue(NodeData node) {
        Metric metric = tbPresenter.getSelectedMetric();
        switch (metric) {
            case CPU: {
                return node.getCpu();
            }
            case MEMORY: {
                return node.getRam();
            }
            default: {
                logger.error("Unknown metric: " + metric);
            }
        }
        return null;
    }
    
}
