package com.file2chart.exceptions.custom;

import com.file2chart.exceptions.ErrorCode;
import com.file2chart.exceptions.http.HttpBadRequestException;

public class InvalidStringValueException extends HttpBadRequestException {
    private static ErrorCode code = ErrorCode.RECORD_VALIDATION_ERROR;

    public InvalidStringValueException() {
        super();
    }

    public InvalidStringValueException(String message, Throwable cause) {
        super(code, message, cause);
    }

    public InvalidStringValueException(String message) {
        super(code, message);
    }

    public InvalidStringValueException(Throwable cause) {
        super(code, cause);
    }
}
