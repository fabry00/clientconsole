package com.console.service.backend;

import com.console.domain.State;
import com.jedux.Action;
import com.jedux.Store;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
@Named("CommandLine")
public class CommandLineAppService implements IBackendService {

    private final Logger logger = Logger.getLogger(CommandLineAppService.class);

    public CommandLineAppService(){
    }
    
    @PostConstruct
    public void init() {
        logger.debug("initialized");
    }

    @Override
    public void start() {
    }
    
    @Override
    public void dispatch(Store<Action, State> store, Action action, Store.NextDispatcher<Action> next) {
        logger.debug("dispatch");
        next.dispatch(action);
        //String json = mGson.toJson(store.getState());
        //mPreferences.edit().putString("data", json).apply();
    }
}
