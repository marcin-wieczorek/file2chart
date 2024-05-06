package com.file2chart.exceptions;

public class InvalidHeaderException extends HttpBadRequestException {
    public InvalidHeaderException() {
        super();
    }

    public InvalidHeaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHeaderException(String message) {
        super(message);
    }

    public InvalidHeaderException(Throwable cause) {
        super(cause);
    }
}
