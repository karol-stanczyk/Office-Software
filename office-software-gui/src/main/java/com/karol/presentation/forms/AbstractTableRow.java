package com.karol.presentation.forms;

public abstract class AbstractTableRow {

    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index + 1;
    }
}
