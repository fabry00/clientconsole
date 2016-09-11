package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.ImmutableAppState;
import com.console.domain.State;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class CloseAction implements IActionHandler {

    private final Logger logger = Logger.getLogger(CloseAction.class);

    @Override
    public ImmutableAppState execute(ImmutableAppState currentState, Action action, ApplicationService appService) {
        logger.debug("Close Action execution");
        if (!currentState.getState().equals(State.STOPPED)) {
            logger.debug("Application not stopped, stopping");
            appService.dispatch(new Action<>(ActionType.STOP, null));
        }
        
        try {
            // Wait all thread exit
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        logger.debug("Exit the application");
        System.exit(0);
        return currentState;
    }

}
