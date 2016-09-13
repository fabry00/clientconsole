package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.AppState;
import com.console.domain.DataReceived;
import com.console.domain.State;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ProcessDataAction implements IActionHandler {

    private Logger logger = Logger.getLogger(ProcessDataAction.class);

    @Override
    public void execute(AppState currentState,
            Action action, ApplicationService appService) {

        DataReceived data = (DataReceived) action.value;
        currentState.setState(State.NEWDATARECEIVED);
        currentState.setDataReceived(data);
    }

}
