package com.file2chart.service.security;

import com.file2chart.config.rest.ApiKeysConfig;
import com.file2chart.exceptions.custom.BadCredentialsException;
import com.file2chart.exceptions.custom.UnsupportedPricingPlanException;
import com.file2chart.model.enums.PricingPlan;
import com.file2chart.service.RequestScrappingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class SecurityService {

    private final ApiKeysConfig apiKeysConfig;
    private final RequestScrappingService requestScrappingService;

    private static final String RAPID_API_SECRET_HEADER_NAME = "X-RapidAPI-Proxy-Secret";
    private static final String RAPID_API_PRICING_PLAN_HEADER_NAME = "X-RapidAPI-Proxy-Pricing-Plan";

    public void validateRapidApiSecretHeaders(HttpServletRequest request) {
        boolean isValid = isHeaderValid(request, RAPID_API_SECRET_HEADER_NAME, apiKeysConfig.getRapidApi());

        if (!isValid) {
            log.error("Cannot find valid header. {'headerName': '{}'}", RAPID_API_SECRET_HEADER_NAME);
            throw new BadCredentialsException("Invalid API Key");
        }
    }

    public void validateRapidApiPricingPlanHeaders(HttpServletRequest request, List<PricingPlan> pricingPlans) {
        List<String> expectedPricingPlans = pricingPlans.stream()
                                                        .map(Object::toString)
                                                        .collect(Collectors.toList());
        boolean isValid = isHeaderValid(request, RAPID_API_PRICING_PLAN_HEADER_NAME, expectedPricingPlans);

        if (!isValid) {
            log.error("The current pricing plan does not allow for this operation. {'headerName': '{}', 'expectedHeaderValues': '{}'}", RAPID_API_PRICING_PLAN_HEADER_NAME, expectedPricingPlans);
            throw new UnsupportedPricingPlanException("The current pricing plan does not allow for this operation");
        }
    }

    public PricingPlan getPricingPlan(HttpServletRequest request) {
        return requestScrappingService.getHeaderValue(request, RAPID_API_PRICING_PLAN_HEADER_NAME)
                                      .map(PricingPlan::valueOf)
                                      .orElseThrow(() -> {
                                          log.error("Cannot find pricing plan in request header. {'headerName': '{}'}", RAPID_API_PRICING_PLAN_HEADER_NAME);
                                          return new BadCredentialsException("Invalid pricing plan");
                                      });
    }

    private boolean isHeaderValid(HttpServletRequest request, String headerName, String expectedHeaderValue) {
        return requestScrappingService.getHeaderValue(request, headerName)
                                      .map(headerValue -> expectedHeaderValue.equals(headerValue))
                                      .orElse(false);
    }

    private boolean isHeaderValid(HttpServletRequest request, String headerName, List<String> expectedHeaderValues) {
        return Stream.of(requestScrappingService.getHeaderValue(request, headerName))
                     .flatMap(Optional::stream)
                     .anyMatch(headerValue -> expectedHeaderValues.stream().anyMatch(headerValue::equals));
    }
}
