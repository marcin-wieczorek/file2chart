package com.file2chart.exceptions.custom;

import com.file2chart.exceptions.ErrorCode;
import com.file2chart.exceptions.http.HttpBadRequestException;

public class InvalidHeaderException extends HttpBadRequestException {
    private static ErrorCode code = ErrorCode.HEADER_VALIDATION_ERROR;

    public InvalidHeaderException() {
        super();
    }

    public InvalidHeaderException(String message, Throwable cause) {
        super(code, message, cause);
    }

    public InvalidHeaderException(String message) {
        super(code, message);
    }

    public InvalidHeaderException(Throwable cause) {
        super(code, cause);
    }
}
