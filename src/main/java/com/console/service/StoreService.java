package com.console.service;

import com.console.domain.ActionType;
import com.console.domain.PersistanceController;
import com.console.domain.State;
import com.console.service.backend.CommandLineAppService;
import com.console.service.backend.IBackendService;
import com.jedux.Action;
import com.jedux.Store;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class StoreService {

    private final Logger logger = Logger.getLogger(StoreService.class);
    private Store<Action, State> store;

    @Inject    
    IBackendService backendService;
    
    
    @PostConstruct
    public void init() {
        logger.debug("init store");

        PersistanceController persistanceController = new PersistanceController();
        State initialState = persistanceController.getSavedState();
        if (initialState == null) {
            initialState = State.getDefault();
        }

        this.store = new Store<>(new State.Reducer(), initialState,
                new com.jedux.Logger("ActionLogger"),
                persistanceController,
                backendService);

    }

    public State state() {
        return store.getState();
    }

    public State dispatch(Action<ActionType, ?> action) {
        return store.dispatch(action);
    }

    public Runnable subscribe(final Runnable r) {
        return store.subscribe(r);
    }
}
