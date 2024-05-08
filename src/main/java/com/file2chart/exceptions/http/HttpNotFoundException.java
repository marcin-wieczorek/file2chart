package com.file2chart.exceptions.http;

import com.file2chart.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HttpNotFoundException extends HttpException {
    private static HttpStatus status = HttpStatus.BAD_REQUEST;

    public HttpNotFoundException() {
        super();
    }

    public HttpNotFoundException(ErrorCode code, String message, Throwable cause) {
        super(status, code, message, cause);
    }

    public HttpNotFoundException(ErrorCode code, String message) {
        super(status, code, message);
    }

    public HttpNotFoundException(ErrorCode code, Throwable cause) {
        super(status, code, cause);
    }
}
