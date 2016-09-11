package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ImmutableAppState;

/**
 *
 * @author fabry
 */
interface IActionHandler {
    
    public ImmutableAppState execute(ImmutableAppState currentState, Action action);
}
