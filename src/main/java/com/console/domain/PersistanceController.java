package com.console.domain;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jedux.Action;
import com.jedux.Store;
import org.apache.log4j.Logger;


public class PersistanceController implements Store.Middleware<Action, State> {

    private final Logger logger = Logger.getLogger(PersistanceController.class);
    //private final Gson mGson;

    public PersistanceController() {
        logger.debug("constructor");
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapterFactory(new GsonAdaptersState());
        //mGson = gsonBuilder.create();
    }

    public State getSavedState() {
        logger.debug("getSavedState");
        //String json = mPreferences.getString("data", "");
        //    return mGson.fromJson(json, ImmutableState.class);
        return null;
    }

    @Override
    public void dispatch(Store<Action, State> store, Action action, Store.NextDispatcher<Action> next) {
        logger.debug("dispatch");
        next.dispatch(action);
        //String json = mGson.toJson(store.getState());
        //mPreferences.edit().putString("data", json).apply();
    }
    
    
}
