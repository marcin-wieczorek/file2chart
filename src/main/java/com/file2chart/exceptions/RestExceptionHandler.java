package com.file2chart.exceptions;

import com.file2chart.exceptions.http.HttpBadRequestException;
import com.file2chart.exceptions.http.HttpException;
import com.file2chart.exceptions.http.HttpNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerException(Exception ex) {
        return buildResponseEntity(buildError(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T extends HttpBadRequestException> ResponseEntity<Object> handleBadRequestException(T ex) {
        return buildResponseEntity(buildError(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpNotFoundException.class)
    public <T extends HttpNotFoundException> ResponseEntity<Object> handleNotFoundExceptionException(T ex) {
        return buildResponseEntity(buildError(ex), HttpStatus.NOT_FOUND);
    }

    private ApiError buildError(Exception ex) {
        return new ApiError(ex);
    }

    private ApiError buildError(HttpException ex) {
        return new ApiError(ex);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }

}
