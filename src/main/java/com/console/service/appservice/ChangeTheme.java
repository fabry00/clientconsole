package com.console.service.appservice;


import com.console.domain.Action;
import com.console.domain.AppState;
import org.apache.log4j.Logger;

class ChangeTheme  implements IActionHandler {

    private final Logger logger = Logger.getLogger(ChangeTheme.class);

    @Override
    public void execute(AppState currentState, Action action, ApplicationService appService) {
        logger.debug("ChangeTheme execution");

        appService.getMainApp().changeTheme();

    }
}
