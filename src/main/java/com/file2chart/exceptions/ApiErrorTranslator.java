package com.file2chart.exceptions;

import com.file2chart.exceptions.http.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class ApiErrorTranslator {
    private final Map<Class, ApiError> apiErrors;
    private static Class DEFAULT_ERROR_RESPONSE_CLASS = HttpServerErrorException.InternalServerError.class;

    ApiErrorTranslator() {
        apiErrors = new HashMap() {{
            put(NoResourceFoundException.class, new ApiError(HttpStatus.FORBIDDEN, ErrorCode.AUTHORIZATION_FORBIDDEN_ACCESS, ErrorCode.AUTHORIZATION_FORBIDDEN_ACCESS.getMessage()));
            put(HttpServerErrorException.InternalServerError.class, new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UNKNOWN, ErrorCode.UNKNOWN.getMessage()));
        }};
    }

    public ApiError getApiError(Class clazz) {
        return Optional.ofNullable(apiErrors.get(clazz))
                       .orElseGet(() -> apiErrors.get(DEFAULT_ERROR_RESPONSE_CLASS));
    }

    public ApiError getApiError(Class clazz, String errorMessage) {
        ApiError apiError = getApiError(clazz);

        if (!StringUtils.isEmpty(errorMessage)) {
            apiError.setDebugMessage(errorMessage);
        }

        return apiError;
    }

    public ApiError getApiError(Exception ex) {
        if (ex instanceof HttpException ex1) {
            return new ApiError(ex1);
        }

        return getApiError(ex.getClass(), ex.getLocalizedMessage());
    }
}
