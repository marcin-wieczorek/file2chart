package com.file2chart.exceptions.custom;

import com.file2chart.exceptions.ErrorCode;
import com.file2chart.exceptions.http.HttpBadRequestException;

public class SerializationException extends HttpBadRequestException {
    private static ErrorCode code = ErrorCode.VALIDATION_HASH_ERROR;

    public SerializationException() {
        super();
    }

    public SerializationException(String message, Throwable cause) {
        super(code, message, cause);
    }

    public SerializationException(String message) {
        super(code, message);
    }

    public SerializationException(Throwable cause) {
        super(code, cause);
    }
}
