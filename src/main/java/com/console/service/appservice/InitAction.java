package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ImmutableAppState;
import com.console.domain.ServiceName;
import com.console.domain.State;
import com.console.service.backend.BackEndServiceException;
import com.console.service.backend.IBackendService;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class InitAction implements IActionHandler {

    private final static State expected = State.NOT_STARTED;
    private final Logger logger = Logger.getLogger(InitAction.class);

    @Override
    public ImmutableAppState execute(ImmutableAppState currentState,
            Action action, ApplicationService appService) {
        logger.debug("Init action execution");

        IBackendService backendService = (IBackendService) appService.getService(ServiceName.BACKEND);

        if (!currentState.getState().equals(expected)) {
            StringBuilder builder
                    = new StringBuilder("Wrong application status. Expected: ");
            builder.append(expected.toString())
                    .append(" but found: ")
                    .append(currentState.getState().toString());
            logger.warn(builder.toString());
            return currentState;
        }

        try {
            backendService.start();
        } catch (BackEndServiceException ex) {
            logger.error(ex);
            return ImmutableAppState.copyOf(currentState).withState(State.ERROR);
        }

        return ImmutableAppState.copyOf(currentState).withState(State.STARTED);
    }
}
