package com.file2chart.exceptions;

import com.file2chart.exceptions.http.HttpBadRequestException;
import com.file2chart.exceptions.http.HttpNotFoundException;
import com.file2chart.exceptions.http.HttpUnauthorizedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler {

    private final ApiErrorTranslator apiErrorTranslator;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerException(Exception ex) {
        return buildResponseEntity(buildError(ex));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public <T extends NoResourceFoundException> ResponseEntity<Object> handleForbiddenException(T ex) {
        return buildResponseEntity(buildError(ex));
    }

    @ExceptionHandler(HttpBadRequestException.class)
    public <T extends HttpBadRequestException> ResponseEntity<Object> handleBadRequestException(T ex) {
        return buildResponseEntity(buildError(ex));
    }

    @ExceptionHandler(HttpNotFoundException.class)
    public <T extends HttpNotFoundException> ResponseEntity<Object> handleNotFoundExceptionException(T ex) {
        return buildResponseEntity(buildError(ex));
    }

    @ExceptionHandler(HttpUnauthorizedException.class)
    public <T extends HttpUnauthorizedException> ResponseEntity<Object> handleUnauthorizedExceptionException(T ex) {
        return buildResponseEntity(buildError(ex));
    }

    public ApiError buildError(Exception ex) {
        return apiErrorTranslator.getApiError(ex);
    }

    public static ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
