package com.file2chart.exceptions;

public enum ErrorCode {
    VALIDATION_ERROR("An error occurred while validating input."),
    VALIDATION_HASH_ERROR("An error occurred while validating hash. Please check if the hash is correct and try again."),
    VALIDATION_RECORD_ERROR("An error occurred while validating records."),
    VALIDATION_HEADER_ERROR("An error occurred while validating headers."),

    FILE_FORMAT_ERROR("An error occurred while validating file format."),
    FILE_INTERPRETER_ERROR("An error occurred while loading file interpreter."),

    AUTHORIZATION_BAD_CREDENTIALS("Bad credentials."),
    AUTHORIZATION_FORBIDDEN_ACCESS("No access for this resource."),
    UNSUPPORTED_PRICING_PLAN("Current pricing plan is not supported for this operation."),

    RESOURCE_NOT_FOUND("Resource not found."),

    UNKNOWN("Unexpected error.");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
