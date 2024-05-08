package com.file2chart.exceptions.custom;

public class KeysNotReadableException extends RuntimeException {

    public KeysNotReadableException(String message) {
        super(message);
    }

    public KeysNotReadableException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeysNotReadableException(Throwable cause) {
        super(cause);
    }

}
