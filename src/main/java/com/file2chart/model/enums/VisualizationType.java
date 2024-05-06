package com.file2chart.model.enums;

public enum VisualizationType {
    EMBEDDED("embedded"),
    IMAGE("image");

    private final String type;

    VisualizationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
