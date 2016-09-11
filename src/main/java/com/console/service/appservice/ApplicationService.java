package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.IAppStateListener;
import com.console.domain.ImmutableAppState;
import com.console.domain.State;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ApplicationService {

    private static final int MAX_THREADS = 5;
    private final Logger logger = Logger.getLogger(ApplicationService.class);

    private ImmutableAppState currentState;
    private final ActionFactory factory = new ActionFactory();

    private final Set<IAppStateListener> listeners = new HashSet<>();

    private final ExecutorService executor
            = Executors.newFixedThreadPool(MAX_THREADS, new AppServiceThreadFactory());

    @PostConstruct
    public void init() {
        logger.debug("init");
        currentState = ImmutableAppState
                .builder()
                .state(State.NOT_STARTED)
                .build();

    }

    public ImmutableAppState getCurrentState() {
        return currentState;
    }

    public void dispatch(Action<ActionType, Object> action) {
        logger.debug("New action: " + action);

        try {
            ImmutableAppState oldState = ImmutableAppState.copyOf(currentState);
            IActionHandler handler = factory.create(action.type);
            currentState = handler.execute(currentState, action);
            logger.debug("New state: " + currentState.getState()
                    + " oldState: " + oldState.getState());
            fireAppStateChange(oldState);
        } catch (ActionNotFoundException ex) {
            logger.error(ex);
        }
    }

    public void subscribe(IAppStateListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(IAppStateListener listener) {
        listeners.remove(listener);
    }

    private void fireAppStateChange(ImmutableAppState oldState) {

        listeners.stream().forEach((listener) -> {
            executor.submit(() -> {
                listener.AppStateChanged(oldState, currentState);
            });
        });
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
