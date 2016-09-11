package com.console.domain;

import java.util.Optional;
import org.immutables.value.Value;

/**
 *
 * @author fabry
 */
@Value.Immutable
public abstract class AppState {
    
    
    public abstract State getState();
    
    public abstract Optional<String> getMessage();
    
    public abstract Optional<DataReceived> getDataReceived();
    
}
