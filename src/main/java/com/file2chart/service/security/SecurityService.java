package com.file2chart.service.security;

import com.file2chart.config.rest.ApiKeysConfig;
import com.file2chart.exceptions.custom.BadCredentialsException;
import com.file2chart.service.RequestScrappingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityService {

    private final ApiKeysConfig apiKeysConfig;
    private final RequestScrappingService requestScrappingService;

    private static final String RAPID_API_SECRET_HEADER_NAME = "X-RapidAPI-Proxy-Secret";
    private static final String RAPID_API_PRICING_PLAN_HEADER_NAME = "X-RapidAPI-Proxy-Pricing-Plan";

    public void validateRapidApiHeaders(HttpServletRequest request) {
        validateHeader(request, RAPID_API_SECRET_HEADER_NAME, apiKeysConfig.getRapidApi());
    }

    private void validateHeader(HttpServletRequest request, String headerName, String expectedHeaderValue) {
        requestScrappingService.getHeaderValue(request, headerName)
                               .filter(headerValue -> expectedHeaderValue.equals(headerValue))
                               .orElseThrow(() -> new BadCredentialsException("Invalid API Key"));
    }
}
