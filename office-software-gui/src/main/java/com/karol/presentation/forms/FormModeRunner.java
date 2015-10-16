package com.karol.presentation.forms;

import com.karol.repository.DatabaseException;
import com.karol.utils.VoidDatabaseFunction;
import com.karol.utils.VoidFunction;

public class FormModeRunner {

    public static void runWithException(VoidDatabaseFunction newMode, VoidFunction editMode, FormMode mode) throws DatabaseException {
        switch (mode) {
            case NEW:
                newMode.run();
                break;
            case EDIT:
                editMode.run();
                break;
        }
    }

    public static void run(VoidFunction newMode, VoidFunction editMode, FormMode mode) {
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
