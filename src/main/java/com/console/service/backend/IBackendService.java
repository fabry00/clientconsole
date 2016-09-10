package com.console.service.backend;

import com.console.domain.State;
import com.jedux.Action;
import com.jedux.Store;

/**
 *
 * @author fabry
 */
public interface IBackendService extends Store.Middleware<Action, State> {
    
    public void start();
}
