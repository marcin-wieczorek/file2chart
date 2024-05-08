package com.file2chart.exceptions.custom;

public class DataCompressionException extends RuntimeException {

    public DataCompressionException(String message) {
        super(message);
    }

    public DataCompressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataCompressionException(Throwable cause) {
        super(cause);
    }

}
