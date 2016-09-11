package com.console.domain;

/**
 *
 * @author fabry
 */
public interface IAppStateListener {

    public void AppStateChanged(AppState oldState, AppState currentState);
}
