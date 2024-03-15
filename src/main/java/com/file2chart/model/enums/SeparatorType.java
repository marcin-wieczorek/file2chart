package com.file2chart.model.enums;

public enum SeparatorType {
    TAB(0),
    SEMICOLON(1),
    COMMA(2);

    private final int typeId;

    SeparatorType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }
}

