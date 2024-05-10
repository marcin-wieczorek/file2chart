package com.file2chart.exceptions;

import com.file2chart.exceptions.http.HttpBadRequestException;
import com.file2chart.exceptions.http.HttpException;
import com.file2chart.exceptions.http.HttpNotFoundException;
import com.file2chart.exceptions.http.HttpUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerException(Exception ex) {
        return buildResponseEntity(buildError(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public <T extends NoResourceFoundException> ResponseEntity<Object> handleBadRequestException(T ex) {
        return buildResponseEntity(buildError(ex), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpBadRequestException.class)
    public <T extends HttpBadRequestException> ResponseEntity<Object> handleBadRequestException(T ex) {
        return buildResponseEntity(buildError(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpNotFoundException.class)
    public <T extends HttpNotFoundException> ResponseEntity<Object> handleNotFoundExceptionException(T ex) {
        return buildResponseEntity(buildError(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpUnauthorizedException.class)
    public <T extends HttpUnauthorizedException> ResponseEntity<Object> handleNotFoundExceptionException(T ex) {
        return buildResponseEntity(buildError(ex), HttpStatus.UNAUTHORIZED);
    }

    public static ApiError buildError(Exception ex) {
        return new ApiError(ex);
    }

    public static ApiError buildError(NoResourceFoundException ex) {
        return new ApiError(ex);
    }

    public static ApiError buildError(HttpException ex) {
        return new ApiError(ex);
    }

    public static ResponseEntity<Object> buildResponseEntity(ApiError apiError, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }

}
