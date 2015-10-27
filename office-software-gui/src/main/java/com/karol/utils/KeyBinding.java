package com.karol.utils;

import com.karol.utils.functions.VoidFunction;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;

public class KeyBinding {

    public static void registerAction(KeyCode code, Parent scene, VoidFunction function) {

        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(code)) {
                function.run();
            }
        });
    }
}
