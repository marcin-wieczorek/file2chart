package com.file2chart.exceptions.http;

import com.file2chart.exceptions.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class HttpException extends RuntimeException {
    private ErrorCode code = ErrorCode.UNKNOWN;
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpException() {
        super();
        setStatus(status);
        setCode(code);
    }

    public HttpException(HttpStatus status, ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        setStatus(status);
        setCode(code);
    }

    public HttpException(HttpStatus status, ErrorCode code, String message) {
        super(message);
        setStatus(status);
        setCode(code);

    }

    public HttpException(HttpStatus status, ErrorCode code, Throwable cause) {
        super(cause);
        setStatus(status);
        setCode(code);
    }

}
