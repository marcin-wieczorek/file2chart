package com.file2chart.exceptions;

public enum ErrorCode {
    RECORD_VALIDATION_ERROR("An error occurred while validating records."),
    HEADER_VALIDATION_ERROR("An error occurred while validating headers."),

    FILE_FORMAT_ERROR("An error occurred while validating file format."),
    FILE_INTERPRETER_ERROR("An error occurred while loading file interpreter."),

    HASH_VALIDATION_ERROR("An error occurred while validating hash. Please check if the hash is correct and try again."),

    AUTHORIZATION_BAD_CREDENTIALS("Bad credentials."),
    AUTHORIZATION_FORBIDDEN_ACCESS("No access for this resource."),

    UNKNOWN("Unexpected error.");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
