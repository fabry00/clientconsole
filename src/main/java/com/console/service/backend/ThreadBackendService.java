package com.console.service.backend;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.ImmutableDataReceived;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ThreadBackendService implements IBackendService {

    private static final int MAX_THREAD = 1;
    private static final int INITIAL_SLEEP = 5; //seconds
    private static final int SCHEDULE_EVERY = 15; //seconds 
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
            String data = "AAAA";
            ImmutableDataReceived datas 
                    = ImmutableDataReceived.builder().data(data).build();
            this.appService.dispatch(new Action<>(ActionType.DATA_RECEIVED, datas));

        }, INITIAL_SLEEP, SCHEDULE_EVERY, TimeUnit.SECONDS);

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
