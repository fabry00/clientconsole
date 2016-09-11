package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.IAppStateListener;
import com.console.domain.ImmutableAppState;
import com.console.domain.ServiceName;
import com.console.domain.State;
import com.console.service.IService;
import com.console.service.backend.ThreadBackendService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javafx.application.Platform;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ApplicationService {

    private final Logger logger = Logger.getLogger(ApplicationService.class);

    private ImmutableAppState currentState;
    private final ActionFactory factory = new ActionFactory();

    private final Set<IAppStateListener> listeners = new HashSet<>();

    private final Map<ServiceName, Object> services = new HashMap<>();

    @PostConstruct
    public void init() {
        logger.debug("init");
        currentState = ImmutableAppState
                .builder()
                .state(State.NOT_STARTED)
                .build();

        initServices();
    }

    public ImmutableAppState getCurrentState() {
        return currentState;
    }

    public void dispatch(Action<ActionType, Object> action) {
        logger.debug("New action: " + action);

        try {
            ImmutableAppState oldState = ImmutableAppState.copyOf(currentState);
            IActionHandler handler = factory.create(action.type);
            currentState = handler.execute(currentState, action, this);

            if (oldState.equals(currentState)) {
                return;
            }
            logger.debug("New state: " + currentState.getState()
                    + " oldState: " + oldState.getState());
            fireAppStateChange(oldState);
        } catch (ActionNotFoundException ex) {
            logger.error(ex);
        }
    }

    public Object getService(ServiceName serviceName) {
        if (!services.containsKey(serviceName)) {
            logger.error("Service not found: " + serviceName);
            return null;
        }

        return services.get(serviceName);
    }

    public void subscribe(IAppStateListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(IAppStateListener listener) {
        listeners.remove(listener);
    }

    public void stopAllServices() {

        services.entrySet().stream().map((entry) -> {
            logger.debug("Stopping service: " + entry.getKey().toString());
            return entry;
        }).forEach((entry) -> {
            ((IService) entry.getValue()).stop();
        });

    }

    private void fireAppStateChange(ImmutableAppState oldState) {
        listeners.stream().forEach((listener) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    listener.AppStateChanged(oldState, currentState);
                }

            });
        });
    }

    private void initServices() {
        this.services.put(ServiceName.BACKEND, new ThreadBackendService(this));

    }

    private static class AppServiceThreadFactory implements ThreadFactory {

        private static final String THREAD_NAME
                = ApplicationService.class.getSimpleName() + "ExecutionThread";

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, THREAD_NAME);
        }
    }

}
