package com.karol.presentation.services;

import javafx.scene.Parent;

public class NavigationService {
    private static Parent currentState;
    private static Parent previousState;

    public static void setCurrentState(Parent state) {
        previousState = currentState;
        currentState = state;
    }

    public static Parent getPreviousState() {
        return previousState;
    }
}
