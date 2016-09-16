package com.console.view.graphdata;

import java.net.URL;
import java.util.*;
import com.console.domain.Metric;
import com.console.domain.Node;
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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author fabry
 */
public class GraphdataPresenter implements Initializable, IToolbarListener {

    private final Logger logger = Logger.getLogger(GraphdataPresenter.class);

    private final ObservableList<XYChart.Series<Date, Object>> seriesList
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
        // By default, the NumberAxis determines its range automatically. 
        // We can overrule this behavior by setting the autoRanging property to 
        // false, and by providing values for the lowerBound and the upperBound
        // setAutoRanging(false); setLowerBoud(..

        if (chart.getXAxis() instanceof NumberAxis) {
            // Force if to show or not always the zero element
            ((NumberAxis) chart.getXAxis()).setForceZeroInRange(false);
        } else if (chart.getXAxis() instanceof CategoryAxis) {

            /*final Comparator<XYChart.Data<String, Number>> comparator
                    = (XYChart.Data<String, Number> o1, XYChart.Data<String, Number> o2)
                    -> o1.getXValue().compareTo(o2.getXValue());
            chart.getData().sort(comparator);*/
        }

        chart.setData(seriesList);

        chart.setTitle(tbPresenter.getSelectedMetric().getTitle());
    }

    @Override
    public void nodesSelectedChanged() {
        logger.debug("nodeSelectedChange");
        List<Node> nodeSelected = tbPresenter.getNodesSelected();

        removeSeriesFromChart(nodeSelected);
        addSeriesToChart(nodeSelected);
    }

    private void addSeriesToChart(List<Node> nodeSelected) {
        List<XYChart.Series<Date, Object>> serieToAdd = new ArrayList<>();

        // Check Serie to add
        for (Node node : nodeSelected) {
            boolean toAdd = true;
            for (XYChart.Series<Date, Object> serie : seriesList) {

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

    private void removeSeriesFromChart(List<Node> nodeSelected) {
        List<Integer> serieToRemove = new ArrayList<>();
        // Check Serie to remove
        if (nodeSelected.isEmpty()) {
            // Delete all displayed series
            removeAllSeries();
        } else {
            int index = 0;
            for (XYChart.Series<Date, Object> serie : seriesList) {
                boolean toRemove = false;
                for (Node node : nodeSelected) {
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

    private String getSerieName(Node node) {
        return node.getNode();
    }

    private XYChart.Series<Date, Object> getSerieToShow(Node node) {
        XYChart.Series<Date, Object> serie = new XYChart.Series();
        serie.setName(getSerieName(node));
        Metric metricSelected = tbPresenter.getSelectedMetric();
        serie.setData(node.getMetric(metricSelected));
        return serie;
    }

}
