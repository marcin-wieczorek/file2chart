package com.file2chart.exceptions;

public class InvalidFileFormatException extends HttpBadRequestException {
    public InvalidFileFormatException() {
        super();
    }

    public InvalidFileFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFileFormatException(String message) {
        super(message);
    }

    public InvalidFileFormatException(Throwable cause) {
        super(cause);
    }
}
