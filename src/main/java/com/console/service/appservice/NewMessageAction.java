package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ImmutableAppState;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class NewMessageAction implements IActionHandler {

    private Logger logger = Logger.getLogger(NewMessageAction.class);

    @Override
    public ImmutableAppState execute(ImmutableAppState currentState, Action action, ApplicationService appService) {
        String message = (String) action.value;
        logger.debug("New application message: " + message);
        return ImmutableAppState.copyOf(currentState).withMessage(message);
    }

}
