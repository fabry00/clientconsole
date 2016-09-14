package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.AppState;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
class NewMessageAction implements IActionHandler {

    private final Logger logger = Logger.getLogger(NewMessageAction.class);

    @Override
    public void execute(AppState currentState, Action action, ApplicationService appService) {
        String message = (String) action.value;
        logger.debug("New application message: " + message);
        currentState.setMessage(message);
    }

}
