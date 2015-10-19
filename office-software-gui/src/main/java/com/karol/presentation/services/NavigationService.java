package com.karol.presentation.services;

import com.karol.presentation.forms.Cleanable;
import javafx.scene.Parent;

import java.util.Optional;

public class NavigationService {
    private static Parent currentState;
    private static Cleanable currentStatePresenter;
    private static Parent previousState;
    private static Cleanable previousStatePresenter;

    public static void setCurrentState(Parent state) {
        previousState = currentState;
        currentState = state;
    }

    public static void setCurrentState(Parent state, Cleanable presenter) {
        setCurrentState(state);
        if (previousStatePresenter == null) {
            previousStatePresenter = presenter;
        } else {
            previousStatePresenter = currentStatePresenter;
        }
        currentStatePresenter = presenter;
    }

    public static Parent getPreviousState() {
        return previousState;
    }

    public static Optional<Cleanable> getPreviousStatePresenter() {
        return Optional.ofNullable(previousStatePresenter);
    }
}
