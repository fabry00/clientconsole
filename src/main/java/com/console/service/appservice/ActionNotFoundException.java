package com.console.service.appservice;

import com.console.domain.ActionType;

/**
 *
 * @author fabry
 */
public class ActionNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ActionNotFoundException(ActionType action) {
        super("ActionNotFound: " + action.toString());
    }
}
