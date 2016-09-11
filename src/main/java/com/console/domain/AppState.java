package com.console.domain;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AppState other = (AppState) o;
        return Objects.equals(getState(), other.getState())
                && Objects.equals(getMessage(), other.getMessage())
                && Objects.equals(getDataReceived(), other.getDataReceived());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getState())
                + Objects.hash(getMessage())
                + Objects.hash(getDataReceived());
    }

}
