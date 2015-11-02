package com.karol.utils;

import com.karol.utils.functions.VoidFunction;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;

public class KeyBinding {

    public static void registerAction(KeyCode code, Node scene, VoidFunction function) {
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(code)) {
                function.run();
            }
        });
    }

    public static void registerActionForAllChildren(KeyCode code, Parent scene, VoidFunction function) {
        if (scene.getChildrenUnmodifiable().size() > 0) {
            scene.getChildrenUnmodifiable().stream()
                    .filter(ch -> !(ch instanceof Parent))
                    .forEach(ch -> registerAction(code, ch, function));
            scene.getChildrenUnmodifiable().stream()
                    .filter(ch -> ch instanceof Parent)
                    .forEach(ch -> registerActionForAllChildren(code, (Parent) ch, function));
        } else {
            registerAction(code, scene, function);
        }
    }
}
