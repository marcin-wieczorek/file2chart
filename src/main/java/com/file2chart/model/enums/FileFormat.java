package com.file2chart.model.enums;

public enum FileFormat {
    CSV("csv");

    private String format;

    FileFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
