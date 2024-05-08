package com.file2chart.exceptions.custom;

public class DataEncryptionException extends RuntimeException {

    public DataEncryptionException(String message) {
        super(message);
    }

    public DataEncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataEncryptionException(Throwable cause) {
        super(cause);
    }

}
