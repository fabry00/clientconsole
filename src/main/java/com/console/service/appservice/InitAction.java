package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ImmutableAppState;
import com.console.domain.State;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class InitAction implements IActionHandler {

    private final static State expected = State.NOT_STARTED;
    private final Logger logger = Logger.getLogger(InitAction.class);

    @Override
    public ImmutableAppState execute(ImmutableAppState currentState, Action action) {
        logger.debug("Init action execution");

        if (!currentState.getState().equals(expected)) {
            StringBuilder builder
                    = new StringBuilder("Wrong application status. Expected: ");
            builder.append(expected.toString())
                    .append(" but found: ")
                    .append(currentState.getState().toString());
            logger.warn(builder.toString());
            return currentState;
        }
        return ImmutableAppState.copyOf(currentState).withState(State.STARTED);
    }

}
