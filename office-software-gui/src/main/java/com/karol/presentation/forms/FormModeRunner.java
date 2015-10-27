package com.karol.presentation.forms;

import com.karol.presentation.navigation.Action;
import com.karol.repository.utils.DatabaseException;
import com.karol.utils.VoidDatabaseFunction;
import com.karol.utils.VoidFunction;

public class FormModeRunner {

    public static void runWithException(VoidDatabaseFunction newMode, VoidDatabaseFunction editMode, Action mode) throws DatabaseException {
        switch (mode) {
            case NEW:
                newMode.run();
                break;
            case EDIT:
                editMode.run();
                break;
        }
    }

    public static void run(VoidFunction newMode, VoidFunction editMode, Action mode) {
        switch (mode) {
            case NEW:
                newMode.run();
                break;
            case EDIT:
                editMode.run();
                break;
        }
    }
}
