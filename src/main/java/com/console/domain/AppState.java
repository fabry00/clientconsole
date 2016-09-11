package com.console.domain;

import java.util.Optional;
import org.immutables.value.Value;

/**
 *
 * @author fabry
 */
@Value.Immutable
public abstract class AppState {
    
    // To remove, just to remember that we can create optionals param
    public abstract Optional<String> getOptionalParam();
    
    public abstract State getState();
    
    public abstract Optional<DataReceived> getDataReceived();
    
}
