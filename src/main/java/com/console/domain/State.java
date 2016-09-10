package com.console.domain;

import com.jedux.Action;
import com.jedux.Store;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import org.apache.log4j.Logger;

//import org.immutables.gson.Gson; --> TO SAVE THE STATE
import org.immutables.value.Value;

@Value.Immutable
//@Gson.TypeAdapters
public abstract class State {

    public abstract Status status();
    
    @Value.Immutable
    //@Gson.TypeAdapters
    public static abstract class Task {
        abstract int id();
        abstract String name();
        abstract boolean checked();
    }

    abstract List<Task> tasks();

    boolean hasChecked() {
        for (Task t : tasks()) {
            if (t.checked()) {
                return true;
            }
        }
        return false;
    }

    public static State getDefault() {
        return ImmutableState.builder().status(Status.UNKWOWN).build();
    }

    public static class Reducer implements Store.Reducer<Action, State> {

        private Logger logger = Logger.getLogger(Reducer.class);
        
        public State reduce(Action action, State old) {
            logger.debug("reduce: "+action.toString());
            return ImmutableState.builder()
                    .from(old)
                    .status(reduceStatus(action, old.status()))
                    .build();
            
        }

        private Status reduceStatus(Action action, Status status) {
            ActionType type = (ActionType) action.type;
            switch (type) {
                case START:{
                    return Status.STARTING;
                }
                default:
                    return status.UNKWOWN;
            }
        }
    }
}
