package com.file2chart.exceptions;

public class InvalidNumericValueException extends HttpBadRequestException {
    public InvalidNumericValueException() {
        super();
    }

    public InvalidNumericValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNumericValueException(String message) {
        super(message);
    }

    public InvalidNumericValueException(Throwable cause) {
        super(cause);
    }
}
