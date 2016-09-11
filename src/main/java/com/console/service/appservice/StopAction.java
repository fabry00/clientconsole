package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ImmutableAppState;
import com.console.domain.State;
import com.console.service.backend.IBackendService;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class StopAction implements IActionHandler {

    private Logger logger = Logger.getLogger(StopAction.class);

    @Override
    public ImmutableAppState execute(ImmutableAppState currentState, Action action, ApplicationService appService) {
        logger.debug("Stop action execution");

        /*if (!currentState.getState().equals(State.STARTED)
                && !currentState.getState().equals(State.PROCESSING)) {
            StringBuilder builder
                    = new StringBuilder("Wrong application status. Expected: ");
            builder.append(State.STARTED.toString())
                    .append(" or ")
                    .append(State.PROCESSING.toString())
                    .append(" but found: ")
                    .append(currentState.getState().toString());
            logger.warn(builder.toString());
            return currentState;
        }*/

        appService.stopAllServices();

        return ImmutableAppState.copyOf(currentState).withState(State.STOPPED);
    }

}
