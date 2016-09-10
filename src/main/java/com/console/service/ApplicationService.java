package com.console.service;

import com.console.domain.ActionType;
import com.console.domain.State;
import com.jedux.Action;
import com.jedux.Store;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ApplicationService  implements Store.Middleware<Action, State> {

    private final Logger logger = Logger.getLogger(ApplicationService.class);
    
    @Override
    public void dispatch(Store<Action, State> store, Action action, Store.NextDispatcher<Action> next) {
        if(action.type.equals(ActionType.CLOSE)) {
            logger.debug("Exiting application");
            System.exit(0);
        }
        
        next.dispatch(action);
    }
    
    
    
}
