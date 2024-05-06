package com.file2chart.exceptions;

public class InvalidStringValueException extends HttpBadRequestException {
    public InvalidStringValueException() {
        super();
    }

    public InvalidStringValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStringValueException(String message) {
        super(message);
    }

    public InvalidStringValueException(Throwable cause) {
        super(cause);
    }
}
