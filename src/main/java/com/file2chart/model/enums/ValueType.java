package com.file2chart.model.enums;

public enum ValueType {
    TEXT(0),
    INTEGER(1),
    BIGINT(2),
    DECIMAL(3),
    PERCENT(4),
    CURRENCY(5),
    DATE(6),
    BOOLEAN(7),
    ID(8),
    OBJECT(9),
    TIMESTAMP(10),
    YEAR(11),
    ARRAY(12),
    INT_OPINION_SCALE(13),
    UUID(14);

    private final int typeId;

    ValueType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }
}

