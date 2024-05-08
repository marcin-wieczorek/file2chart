package com.file2chart.exceptions.custom;

import com.file2chart.exceptions.ErrorCode;
import com.file2chart.exceptions.http.HttpBadRequestException;

public class InvalidNumericValueException extends HttpBadRequestException {
    private static ErrorCode code = ErrorCode.ASD;

    public InvalidNumericValueException() {
        super();
    }

    public InvalidNumericValueException(String message, Throwable cause) {
        super(code, message, cause);
    }

    public InvalidNumericValueException(String message) {
        super(code, message);
    }

    public InvalidNumericValueException(Throwable cause) {
        super(code, cause);
    }
}
