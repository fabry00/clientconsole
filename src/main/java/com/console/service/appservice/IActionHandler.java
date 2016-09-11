package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ImmutableAppState;
import com.console.domain.ServiceName;
import java.util.Map;

/**
 *
 * @author fabry
 */
interface IActionHandler {

    public ImmutableAppState execute(ImmutableAppState currentState,
            Action action, Map<ServiceName, Object> services);
}
