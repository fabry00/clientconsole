package com.jedux;


public class Logger<A, S> implements Store.Middleware<A, S> {

    private final String tag;
    private final org.apache.log4j.Logger logger 
            = org.apache.log4j.Logger.getLogger(Logger.class);

    public Logger(String tag) {
        this.tag = tag;
    }

    @Override
    public void dispatch(Store<A, S> store, A action, Store.NextDispatcher<A> next) {
        logger.debug(tag + "--> " + action.toString());
        next.dispatch(action);
        logger.debug(tag + "<-- " + store.getState().toString());
    }
}
