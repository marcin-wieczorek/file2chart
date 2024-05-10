package com.file2chart.exceptions.http;

import com.file2chart.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class HttpUnauthorizedException extends HttpException {
    private static HttpStatus status = HttpStatus.UNAUTHORIZED;

    public HttpUnauthorizedException() {
        super();
    }

    public HttpUnauthorizedException(ErrorCode code, String message, Throwable cause) {
        super(status, code, message, cause);
    }

    public HttpUnauthorizedException(ErrorCode code, String message) {
        super(status, code, message);
    }

    public HttpUnauthorizedException(ErrorCode code, Throwable cause) {
        super(status, code, cause);
    }
}
