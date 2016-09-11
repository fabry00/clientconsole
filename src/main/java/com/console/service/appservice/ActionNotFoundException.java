package com.console.service.appservice;

import com.console.domain.ActionType;

/**
 *
 * @author fabry
 */
public class ActionNotFoundException  extends Exception{
    
    public ActionNotFoundException(ActionType action){
        super("ActionNotFound: "+action.toString());
    }
}
