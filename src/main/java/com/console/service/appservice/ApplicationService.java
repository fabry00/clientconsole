package com.console.service.appservice;

import com.console.App;
import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.ServiceName;
import com.console.domain.State;
import com.console.service.IService;
import com.console.service.backend.ThreadBackendService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.application.Platform;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ApplicationService {

    private final Logger logger = Logger.getLogger(ApplicationService.class);

    private final List<AppState> oldStates = new ArrayList<>();
    private final AppState currentState = new AppState();
    private final ActionFactory factory = new ActionFactory();

    private final Set<IAppStateListener> listeners = new HashSet<>();
    private final Map<State,Set<IAppStateListener>> listenersToState = new HashMap<>();

    private final Map<ServiceName, Object> services = new HashMap<>();

    private App mainApp;

    @PostConstruct
    public void init() {
        logger.debug("init");
        currentState.setState(State.NOT_STARTED);

        initServices();
    }

    public AppState getCurrentState() {
        return currentState;
    }

    public void dispatch(final Action<ActionType, Object> action) {
        logger.debug("New action: " + action);
        final ApplicationService self = this;

        Platform.runLater(() -> {
                try {
                    AppState oldState = currentState.clone();
                    oldStates.add(oldState);
                    IActionHandler handler = factory.create(action.type);

                    handler.execute(currentState, action, self);

                    logger.debug("Action executed");
                    fireAppStateChange(oldState);
                } catch (ActionNotFoundException ex) {
                    logger.error(ex);
                }
        });

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

    public void subscribeToStae(IAppStateListener listener, State type) {

        Set<IAppStateListener> listeners;
        if(!listenersToState.containsKey(type)) {
            listeners = new HashSet<>();
            listenersToState.put(type,listeners);
        }
        else {
            listeners = listenersToState.get(type);
        }

        listeners.add(listener);
    }

    /*public void unsubscribe(IAppStateListener listener) {
        listeners.remove(listener);
    }*/

    public void stopAllServices() {

        services.entrySet().stream().map((entry) -> {
            logger.debug("Stopping service: " + entry.getKey().toString());
            return entry;
        }).forEach((entry) -> {
            ((IService) entry.getValue()).stop();
        });

    }

    public void  setMainApp(App app) {
        this.mainApp = app;
    }

    public App getMainApp() {
        return mainApp;
    }

    private void fireAppStateChange(AppState oldState) {
        logger.debug("fireAppStateChange");

        // Fire to whome is subscribed to all events
        listeners.stream().forEach((listener) -> Platform.runLater(() -> {
            logger.debug("fire state change to: "+listener.getClass().getSimpleName());
            listener.AppStateChanged(oldState, currentState);
        }));

        Set<IAppStateListener> specificListeners = listenersToState.get(currentState.getState());
        if(specificListeners == null) {
            return;
        }

        specificListeners.stream().forEach((listener) -> Platform.runLater(() -> {
            listener.AppStateChanged(oldState, currentState);
        }));

    }

    private void initServices() {
        this.services.put(ServiceName.BACKEND, new ThreadBackendService(this));

    }
}
