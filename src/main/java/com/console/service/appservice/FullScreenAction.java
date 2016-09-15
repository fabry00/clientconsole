package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.AppState;

/**
 *
 * @author fabry
 */
public class FullScreenAction implements IActionHandler {

    @Override
    public void execute(AppState currentState, Action action, ApplicationService appService) {
        appService.getMainApp().switchToFullScreen((boolean) action.value);
    }

}
