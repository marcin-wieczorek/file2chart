package com.file2chart.exceptions.custom;

import com.file2chart.exceptions.ErrorCode;
import com.file2chart.exceptions.http.HttpUnauthorizedException;

public class BadCredentialsException extends HttpUnauthorizedException {
    private static ErrorCode code = ErrorCode.AUTHORIZATION_BAD_CREDENTIALS;

    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(String message, Throwable cause) {
        super(code, message, cause);
    }

    public BadCredentialsException(String message) {
        super(code, message);
    }

    public BadCredentialsException(Throwable cause) {
        super(code, cause);
    }
}
