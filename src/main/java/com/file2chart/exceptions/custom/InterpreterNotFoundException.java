package com.file2chart.exceptions.custom;

import com.file2chart.exceptions.ErrorCode;
import com.file2chart.exceptions.http.HttpNotFoundException;

public class InterpreterNotFoundException extends HttpNotFoundException {
    private static ErrorCode code = ErrorCode.FILE_INTERPRETER_ERROR;

    public InterpreterNotFoundException() {
        super();
    }

    public InterpreterNotFoundException(String message, Throwable cause) {
        super(code, message, cause);
    }

    public InterpreterNotFoundException(String message) {
        super(code, message);
    }

    public InterpreterNotFoundException(Throwable cause) {
        super(code, cause);
    }
}
