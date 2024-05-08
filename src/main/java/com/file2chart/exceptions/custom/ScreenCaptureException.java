package com.file2chart.exceptions.custom;

public class ScreenCaptureException extends RuntimeException {

    public ScreenCaptureException(String message) {
        super(message);
    }

    public ScreenCaptureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScreenCaptureException(Throwable cause) {
        super(cause);
    }

}
