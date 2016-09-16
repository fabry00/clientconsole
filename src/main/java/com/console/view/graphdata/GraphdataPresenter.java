package com.console.view.graphdata;

import java.net.URL;
import java.util.*;
import com.console.domain.Metric;
import com.console.domain.NodeData;
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
public class GraphdataPresenter implements Initializable, IToolbarListener {

    private final Logger logger = Logger.getLogger(GraphdataPresenter.class);

    private final ObservableList<XYChart.Series<Object, Object>> seriesList
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

        initToolbar();
        initChart();
    }

    @Override
    public void metricSelected(Metric metric) {
        removeAllSeries();
        // Fire node nodesSelectedChanged to re-add all the series with the right metric
        nodesSelectedChanged();
        chart.setTitle(metric.getTitle());

    }

    @Override
    public void resetSeriesClicked() {
        // As are observable, deleting the chart series we dalete also the node data
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
    public void nodesSelectedChanged() {
        logger.debug("nodeSelectedChange");
        List<NodeData> nodeSelected = tbPresenter.getNodesSelected();

        removeSeriesFromChart(nodeSelected);
        addSeriesToChart(nodeSelected);
    }

    private void addSeriesToChart(List<NodeData> nodeSelected) {
        List<XYChart.Series<Object, Object>> serieToAdd = new ArrayList<>();

        // Check Serie to add
        for (NodeData node : nodeSelected) {
            boolean toAdd = true;
            for (XYChart.Series<Object, Object> serie : seriesList) {

                if (serie.getName().equals(node.getNode())) {
                    toAdd = false;
                    break;
                }
            }
            if (toAdd) {
                // Serie not displayed --> add
                serieToAdd.add(getSerieToShow(node));
            }
        }

        logger.debug("Series to add: " + serieToAdd.size());

        // Add Serie
        seriesList.addAll(serieToAdd);
    }

    private void removeSeriesFromChart(List<NodeData> nodeSelected) {
        List<Integer> serieToRemove = new ArrayList<>();
        // Check Serie to remove
        if (nodeSelected.isEmpty()) {
            // Delete all displayed series
            removeAllSeries();
        } else {
            int index = 0;
            for (XYChart.Series<Object, Object> serie : seriesList) {
                boolean toRemove = false;
                for (NodeData node : nodeSelected) {
                    if (serie.getName().equals(node.getNode())) {
                        toRemove = true;
                        break;
                    }
                }
                if (!toRemove) {
                    // Serie not displayed --> add
                    serieToRemove.add(index);
                }
                index++;
            }
        }

        // Remove series
        serieToRemove.stream().forEach((i) -> {
            seriesList.remove(seriesList.get(i));
        });
    }

    private void removeAllSeries() {
        List<Integer> serieToRemove = new ArrayList<>();
        for (int i = 0; i < seriesList.size(); i++) {
            serieToRemove.add(i);
        }
        serieToRemove.stream().forEach((i) -> {
            seriesList.remove(seriesList.get(i));
        });
    }

    private String getSerieName(NodeData node) {
        return node.getNode();
    }

    private XYChart.Series<Object, Object> getSerieToShow(NodeData node) {
        XYChart.Series<Object, Object> serie = new XYChart.Series();
        serie.setName(getSerieName(node));
        Metric metricSelected = tbPresenter.getSelectedMetric();
        serie.setData(node.getMetric(metricSelected));
        return serie;
    }

}
