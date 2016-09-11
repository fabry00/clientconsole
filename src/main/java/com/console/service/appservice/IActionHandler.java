package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.AppState;
import com.console.domain.ImmutableAppState;
import org.immutables.value.Value;

/**
 *
 * @author fabry
 */
interface IActionHandler {
    
    public ImmutableAppState execute(ImmutableAppState currentState, Action action);
}
