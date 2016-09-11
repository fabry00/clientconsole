package com.console.service.appservice;

import com.console.domain.ActionType;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
class ActionFactory {

    private static final Map<ActionType, IActionHandler> MAPPER
            = new HashMap<ActionType, IActionHandler>() {
        {
            put(ActionType.START,new InitAction());
            put(ActionType.STOP,new StopAction());
            put(ActionType.STATUS_CHANGED, new StatusChangedAction());
            put(ActionType.DATA_RECEIVED, new ProcessDataAction());
            put(ActionType.CLOSE, new CloseAction());
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
