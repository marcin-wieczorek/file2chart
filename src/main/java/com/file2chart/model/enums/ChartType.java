package com.file2chart.model.enums;

public enum ChartType {
    BAR("bar"),
    DOUGHNUT("doughnut"),
    LINE("line"),
    PIE("pie"),
    POLAR_AREA("polar-area"),
    RADAR("radar");

    private String type;

    ChartType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ChartType getByType(String type) {
        for (ChartType chartType : ChartType.values()) {
            if (chartType.getType().equals(type)) {
                return chartType;
            }
        }
        return null;
    }
}
