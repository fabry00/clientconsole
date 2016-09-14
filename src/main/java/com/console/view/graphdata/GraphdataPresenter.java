package com.console.view.graphdata;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
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

/**
 *
 * @author fabry
 */
public class GraphdataPresenter implements Initializable, IAppStateListener {

    private final Logger logger = Logger.getLogger(GraphdataPresenter.class);
    //http://fxexperience.com/2012/01/curve-fitting-and-styling-areachart/
    @FXML
    AreaChart chart;

    @Inject
    ApplicationService appService;


    private XYChart.Series seriesApril;
    private XYChart.Series seriesMay;
    private int currentIndex = 0;
    private int maxElements = 20;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chart.setTitle("Temperature Monitoring (in Degrees C)");

        appService.subscribeToStae(this,State.NEWDATARECEIVED);

       /* seriesApril= new XYChart.Series();
        seriesApril.setName("April");
        seriesApril.getData().add(new XYChart.Data(1, 4));
        seriesApril.getData().add(new XYChart.Data(3, 10));
        seriesApril.getData().add(new XYChart.Data(6, 15));
        seriesApril.getData().add(new XYChart.Data(9, 8));
        seriesApril.getData().add(new XYChart.Data(12, 5));
        seriesApril.getData().add(new XYChart.Data(15, 18));
        seriesApril.getData().add(new XYChart.Data(18, 15));
        seriesApril.getData().add(new XYChart.Data(21, 13));
        seriesApril.getData().add(new XYChart.Data(24, 19));
        seriesApril.getData().add(new XYChart.Data(27, 21));
        seriesApril.getData().add(new XYChart.Data(30, 21));

        seriesMay = new XYChart.Series();
        seriesMay.setName("May");
        seriesMay.getData().add(new XYChart.Data(1, 20));
        seriesMay.getData().add(new XYChart.Data(3, 15));
        seriesMay.getData().add(new XYChart.Data(6, 13));
        seriesMay.getData().add(new XYChart.Data(9, 12));
        seriesMay.getData().add(new XYChart.Data(12, 14));
        seriesMay.getData().add(new XYChart.Data(15, 18));
        seriesMay.getData().add(new XYChart.Data(18, 25));
        seriesMay.getData().add(new XYChart.Data(21, 25));
        seriesMay.getData().add(new XYChart.Data(24, 23));
        seriesMay.getData().add(new XYChart.Data(27, 26));
        seriesMay.getData().add(new XYChart.Data(31, 26));
        //chart.getData().addAll(seriesApril, seriesMay);*/


        seriesApril= new XYChart.Series();
        seriesMay = new XYChart.Series();
        seriesMay.setName("May");
        seriesApril.setName("April");
        ObservableList<XYChart.Series<Integer, Integer>> datas = FXCollections.observableArrayList();
        datas.addAll(seriesMay,seriesApril);
        chart.setLegendSide(Side.LEFT);
        chart.setLegendVisible(true);

        // Force if to show always the zero element
        ((NumberAxis)chart.getXAxis()).setForceZeroInRange(false);
        chart.setData(datas);


    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        if(currentState.getState().equals(State.NEWDATARECEIVED)) {
            logger.debug("NEW DATA RECEIVED: "+currentIndex);
            Integer randomX = currentIndex;
            Integer randomY = new Random().nextInt(200);
            if(currentIndex > maxElements) {
                seriesApril.getData().remove(0);
                seriesMay.getData().remove(0);
            }

            seriesApril.getData().add(new XYChart.Data(randomX,randomY));

            randomY = new Random().nextInt(200);
            seriesMay.getData().add(new XYChart.Data(randomX,randomY));

            currentIndex++;
        }
    }
}
