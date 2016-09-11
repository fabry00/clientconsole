package com.console.service.backend;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ThreadBackendService implements IBackendService {

    private static final int MAX_THREAD = 1;
    private static final int INITIAL_SLEEP = 5; //seconds
    private static final int SCHEDULE_EVERY = 15; //seconds 

    private final Logger logger = Logger.getLogger(ThreadBackendService.class);

    private final ScheduledExecutorService executor
            = Executors.newScheduledThreadPool(MAX_THREAD);

    @Inject
    ApplicationService appService;

    @PostConstruct
    public void init() {
        logger.debug("initialized");
    }

    @Override
    public void start() throws BackEndServiceException {
        logger.debug("Starting Thread backendService");
        executor.scheduleAtFixedRate(() -> {

            if (appService.getCurrentState().getState().equals(State.CONNECTING)) {
                logger.debug("Backend service started");
                appService.dispatch(new Action<>(ActionType.STATUS_CHANGED, State.PROCESSING));
            } else {
                logger.debug("new data to process");
            }

        }, INITIAL_SLEEP, SCHEDULE_EVERY, TimeUnit.SECONDS);

    }

}
