package com.console.service.backend;

import com.console.domain.ActionType;
import com.console.domain.State;
import com.console.domain.Status;
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
        if(action.type.equals(ActionType.START)){
            logger.debug("Current status: "+store.getState().status().getLabel());
            if(store.getState().status().equals(Status.STARTED)) {
                logger.info("backend already stared");
                return;
            }
            logger.debug("start backend");
            
            store.dispatch(new Action<>(ActionType.STARTED, null));
            return;
        }
        if(action.type.equals(ActionType.STOP)){
            logger.debug("stop backend");
           // store.dispatch(new Action<>(ActionType.STARTED, null));
           return;
        }
        
        next.dispatch(action);
        
    }
}
