package com.console.service.backend;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.DataReceived;
import com.console.service.appservice.ApplicationService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ThreadBackendService implements IBackendService {

    private static final int MAX_THREAD = 1;
    private static final int INITIAL_SLEEP = 2; //seconds
    private static final int SCHEDULE_EVERY = 1; //seconds
    private static final int SHUTDOWN_TIMEOUT = 3; //seconds

    private final Logger logger = Logger.getLogger(ThreadBackendService.class);

    private final ScheduledExecutorService executor
            = Executors.newScheduledThreadPool(MAX_THREAD);

    private ApplicationService appService;

    public ThreadBackendService(ApplicationService appService) {
        this.appService = appService;
    }

    @Override
    public void start() throws BackEndServiceException {
        logger.debug("Starting Thread backendService");

        executor.scheduleAtFixedRate(() -> {

            logger.debug("new data to process");
           // this.appService.dispatch(new Action<>(ActionType.NEW_MESSAGE, "Waiting for data...."));

            String data = "AAAA";
            DataReceived dataRecieved = new DataReceived();
            dataRecieved.setData(data);

            this.appService.dispatch(new Action<>(ActionType.DATA_RECEIVED, dataRecieved));

        }, INITIAL_SLEEP, SCHEDULE_EVERY, TimeUnit.SECONDS);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                appService.dispatch(new Action<>(ActionType.NEW_MESSAGE, "Starting the console application"));
            }
        });

    }

    @Override
    public void stop() {
        logger.debug("Shutting down backend  thread");
        executor.shutdownNow();
        try {
            logger.debug("Wait for tasks termination");
            executor.awaitTermination(SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
    }

}
