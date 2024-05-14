package com.file2chart.service.security;

import com.file2chart.config.rest.ApiKeysConfig;
import com.file2chart.exceptions.custom.BadCredentialsException;
import com.file2chart.exceptions.custom.UnsupportedPricingPlanException;
import com.file2chart.model.enums.PricingPlan;
import com.file2chart.service.RequestScrappingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class SecurityService {

    private final ApiKeysConfig apiKeysConfig;
    private final RequestScrappingService requestScrappingService;

    private static final String RAPID_API_SECRET_HEADER_NAME = "X-RapidAPI-Proxy-Secret";
    private static final String RAPID_API_PRICING_PLAN_HEADER_NAME = "X-RapidAPI-Proxy-Pricing-Plan";

    public void validateRapidApiSecretHeaders(HttpServletRequest request) {
        validateHeader(request, RAPID_API_SECRET_HEADER_NAME, apiKeysConfig.getRapidApi());
    }

    public void validateRapidApiPricingPlanHeaders(HttpServletRequest request) {
        validateHeader(request, RAPID_API_PRICING_PLAN_HEADER_NAME, PricingPlan.PRO.toString(), PricingPlan.MEGA.toString());
    }

    public PricingPlan getPricingPlan(HttpServletRequest request) {
        return requestScrappingService.getHeaderValue(request, RAPID_API_PRICING_PLAN_HEADER_NAME)
                                      .map(PricingPlan::valueOf)
                                      .orElseThrow(() -> new BadCredentialsException("Invalid Pricing Plan"));
    }

    private void validateHeader(HttpServletRequest request, String headerName, String expectedHeaderValue) {
        requestScrappingService.getHeaderValue(request, headerName)
                               .filter(headerValue -> expectedHeaderValue.equals(headerValue))
                               .orElseThrow(() -> new BadCredentialsException("Invalid API Key"));
    }

    private void validateHeader(HttpServletRequest request, String headerName, String ...expectedHeaderValue) {
        boolean isValid = Stream.of(requestScrappingService.getHeaderValue(request, headerName))
                                .flatMap(Optional::stream)
                                .anyMatch(headerValue -> Arrays.stream(expectedHeaderValue).anyMatch(headerValue::equals));

        if (!isValid) {
            throw new UnsupportedPricingPlanException("Invalid Pricing Plan");
        }
    }
}
