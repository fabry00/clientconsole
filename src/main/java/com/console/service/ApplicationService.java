package com.console.service;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.domain.AppState;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class ApplicationService {
    
    private final Logger logger = Logger.getLogger(ApplicationService.class);
    
    
    public void statusChanged(AppState state) {
        logger.debug("New action: " + state);
    }
    
    public void dispatch(Action<ActionType, Object> action) {
        logger.debug("New action: " + action);
    }
    
}
