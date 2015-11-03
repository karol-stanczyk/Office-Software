package com.karol.presentation.forms;

import com.karol.presentation.navigation.Action;
import com.karol.utils.functions.VoidFunction;

public class FormModeRunner {

    private VoidFunction newModeFunction;
    private VoidFunction editModeFunction;

    private FormModeRunner() {
    }

    public static FormModeRunner actions() {
        return new FormModeRunner();
    }

    public FormModeRunner inNewMode(VoidFunction function) {
        this.newModeFunction = function;
        return this;
    }

    public FormModeRunner inEditMode(VoidFunction function) {
        this.editModeFunction = function;
        return this;
    }

    public void run(Action action) {
        switch (action) {
            case NEW: {
                newModeFunction.run();
            }
            break;
            case EDIT: {
                editModeFunction.run();
            }
            break;
        }
    }

}
