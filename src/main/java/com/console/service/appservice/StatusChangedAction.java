package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ImmutableAppState;
import com.console.domain.ServiceName;
import com.console.domain.State;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class StatusChangedAction implements IActionHandler {

    private Logger logger = Logger.getLogger(StatusChangedAction.class);

    @Override
    public ImmutableAppState execute(ImmutableAppState currentState,
            Action action, ApplicationService appService) {
        logger.debug("StatusChangedAction execution");
        State newState = (State) action.value;
        return ImmutableAppState
                .builder()
                .state(newState)
                .build();
    }

}
