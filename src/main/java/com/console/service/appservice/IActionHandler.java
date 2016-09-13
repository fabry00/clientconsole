package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.AppState;

/**
 *
 * @author fabry
 */
interface IActionHandler {

    public void execute(AppState currentState,
            Action action, ApplicationService appService);
}
