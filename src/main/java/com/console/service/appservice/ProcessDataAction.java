package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.DataReceived;
import com.console.domain.ImmutableAppState;
import com.console.domain.State;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ProcessDataAction implements IActionHandler {

    private Logger logger = Logger.getLogger(ProcessDataAction.class);

    @Override
    public ImmutableAppState execute(ImmutableAppState currentState,
            Action action, ApplicationService appService) {

        DataReceived data = (DataReceived)action.value;
       ImmutableAppState newState 
                = ImmutableAppState.copyOf(currentState)
                .withState(State.NEWDATARECEIVED)
                .withDataReceived(data);
       return newState;
        
    }

}
