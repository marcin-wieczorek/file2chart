package com.file2chart.aspect.rapidapi.secure;

import com.file2chart.model.enums.PricingPlan;
import com.file2chart.service.security.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Profile("prod")
@Aspect
@Component
@AllArgsConstructor
public class SecuredRapidApiAspect {

    private final SecurityService securityService;

    @Around("@annotation(com.file2chart.aspect.rapidapi.secure.SecuredRapidApiCall)")
    public Object validateRapidApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof HttpServletRequest request) {
                securityService.validateRapidApiSecretHeaders(request);
            }
        }

        return joinPoint.proceed();
    }

    @Around("@annotation(com.file2chart.aspect.rapidapi.secure.SecuredRapidApiPricingPlan)")
    public Object validateRapidApiPricingPlan(ProceedingJoinPoint joinPoint) throws Throwable {

        SecuredRapidApiPricingPlan annotation = Arrays.stream(joinPoint.getTarget().getClass().getMethods())
              .filter(method -> StringUtils.equalsAny(method.getName(), joinPoint.getSignature().getName()))
              .findFirst()
              .map(method -> method.getAnnotation(SecuredRapidApiPricingPlan.class))
              .orElseThrow(() -> new RuntimeException("Method " + joinPoint.getSignature().getName() + " not found"));

        PricingPlan[] arguments = annotation.pricingPlans();
        List<PricingPlan> pricingPlans = List.of(arguments);

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof HttpServletRequest request) {
                securityService.validateRapidApiPricingPlanHeaders(request, pricingPlans);
            }
        }

        return joinPoint.proceed();
    }
}
