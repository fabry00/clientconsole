package com.console;

import com.console.view.dashboard.DashboardView;
import com.airhacks.afterburner.injection.Injector;
import com.console.service.StoreService;
import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author adam-bien.com
 */
public class App extends Application {

    private final Logger logger = Logger.getLogger(App.class);
    private static final String APP_TITLE = "Console";
    private static final String APP_CSS = "app.css";
    private static final String LOG_CONF = "log4j.properties";
    
    @Inject
    // Init store
    private StoreService store;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        logger.debug("start");
        initLogger();
        initConfiguration();
        initStage(stage);

    }

    @Override
    public void stop() throws Exception {
        logger.debug("stop");
        Injector.forgetAll();
    }

    private void initStage(Stage stage) {
        logger.debug("initStage");
        DashboardView appView = new DashboardView();
        Scene scene = new Scene(appView.getView());
        stage.setTitle(APP_TITLE);
        final String uri = getClass().getResource(APP_CSS).toExternalForm();
        scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.show();
        
    }

    private void initLogger() {
        File log4jfile = new File(LOG_CONF);
        if (!log4jfile.exists()) {
            System.err.println(LOG_CONF);
        }
        PropertyConfigurator.configure(log4jfile.getAbsolutePath());
    }

    private void initConfiguration() {
        logger.debug("initConfiguration");
        /*
         * Properties of any type can be easily injected.
         */
        LocalDate date = LocalDate.of(4242, Month.JULY, 21);
        Map<Object, Object> customProperties = new HashMap<>();
        customProperties.put("date", date);
        /*
         * any function which accepts an Object as key and returns
         * and return an Object as result can be used as source.
         */
        Injector.setConfigurationSource(customProperties::get);

        System.setProperty("happyEnding", " Enjoy the flight!");
    }
}
