package com.file2chart.exceptions.custom;

import com.file2chart.exceptions.ErrorCode;
import com.file2chart.exceptions.http.HttpForbiddenAccessException;

public class NoResourceFoundException extends HttpForbiddenAccessException {
    private static ErrorCode code = ErrorCode.AUTHORIZATION_FORBIDDEN_ACCESS;

    public NoResourceFoundException() {
        super();
    }

    public NoResourceFoundException(String message, Throwable cause) {
        super(code, message, cause);
    }

    public NoResourceFoundException(String message) {
        super(code, message);
    }

    public NoResourceFoundException(Throwable cause) {
        super(code, cause);
    }
}
