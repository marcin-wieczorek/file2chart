package com.file2chart.exceptions;

public enum ErrorCode {
    ASD(""),
    UNKNOWN("Unexpected error");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
