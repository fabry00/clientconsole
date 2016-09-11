package com.console.service.appservice;

import com.console.domain.Action;
import com.console.domain.ActionType;
import com.console.service.backend.IBackendService;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
class ActionFactory {

    @Inject
    ApplicationService service;
  
    
    private static final Map<ActionType, IActionHandler> MAPPER
            = new HashMap<ActionType, IActionHandler>() {
        {
            put(ActionType.START,new InitAction());
            put(ActionType.STATUS_CHANGED, new StatusChangedAction());
        }
    };

    public IActionHandler create(ActionType type) throws ActionNotFoundException{
        logger.debug("Creating action: "+type);
        
        if(!MAPPER.containsKey(type)) {
            throw new ActionNotFoundException(type);
        }
        return MAPPER.get(type);
    }
    
    private final Logger logger = Logger.getLogger(ActionFactory.class);
}
