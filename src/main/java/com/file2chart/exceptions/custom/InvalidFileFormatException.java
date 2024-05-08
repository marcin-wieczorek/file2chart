package com.file2chart.exceptions.custom;

import com.file2chart.exceptions.ErrorCode;
import com.file2chart.exceptions.http.HttpBadRequestException;

public class InvalidFileFormatException extends HttpBadRequestException {
    private static ErrorCode code = ErrorCode.FILE_FORMAT_ERROR;

    public InvalidFileFormatException() {
        super();
    }

    public InvalidFileFormatException(String message, Throwable cause) {
        super(code, message, cause);
    }

    public InvalidFileFormatException(String message) {
        super(code, message);
    }

    public InvalidFileFormatException(Throwable cause) {
        super(code, cause);
    }
}
