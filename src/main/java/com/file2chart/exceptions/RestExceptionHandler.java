package com.file2chart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerException(Exception ex) {
        return buildResponseEntity(buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(HttpBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T extends HttpBadRequestException> ResponseEntity<Object> handleBadRequestException(T ex) {
        return buildResponseEntity(buildError(HttpStatus.BAD_REQUEST, "Bad request", ex));
    }

    @ExceptionHandler(HttpNotFoundException.class)
    public <T extends HttpNotFoundException> ResponseEntity<Object> handleNotFoundExceptionException(T ex) {
        return buildResponseEntity(buildError(HttpStatus.NOT_FOUND, "Not found", ex));
    }

    private ApiError buildError(HttpStatus status, Exception ex) {
        return new ApiError(status, ex);
    }

    private ApiError buildError(HttpStatus status, String message, Exception ex) {
        return new ApiError(status, message, ex);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
