package com.file2chart.exceptions.custom;

public class JsonConversionException extends RuntimeException {

    public JsonConversionException(String message) {
        super(message);
    }

    public JsonConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonConversionException(Throwable cause) {
        super(cause);
    }

}
