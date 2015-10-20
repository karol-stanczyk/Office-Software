package com.karol.presentation.forms;

import com.karol.utils.Bundles;

public class AbstractComboBoxEnum<T> {

    private T value;

    public AbstractComboBoxEnum(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Bundles.get(value.toString().toLowerCase());
    }
}
