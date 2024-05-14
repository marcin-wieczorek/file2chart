package com.file2chart.service.security;

import com.file2chart.config.rest.ApiKeysConfig;
import com.file2chart.exceptions.ApiErrorTranslator;
import com.file2chart.exceptions.custom.BadCredentialsException;
import com.file2chart.service.utils.JsonConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityService {

    private final ApiKeysConfig apiKeysConfig;
    private final JsonConverter converter;
    private final ApiErrorTranslator apiErrorTranslator;

    private static final String RAPID_API_SECRET_HEADER_NAME = "X-RapidAPI-Proxy-Secret";

    public void validateHeaders(HttpServletRequest request) {
        validateHeader(request, RAPID_API_SECRET_HEADER_NAME, apiKeysConfig.getRapidApi());
    }

    private boolean validateHeader(HttpServletRequest request, String headerName, String expectedHeaderValue) {
        String headerValue = request.getHeader(headerName);
        if (headerValue == null || !headerValue.equals(expectedHeaderValue)) {
            throw new BadCredentialsException("Invalid API Key");
        }
        return expectedHeaderValue != null && expectedHeaderValue.equals(expectedHeaderValue);
    }
}
