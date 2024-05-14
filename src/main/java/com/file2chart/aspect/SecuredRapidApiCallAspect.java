package com.file2chart.aspect;

import com.file2chart.service.security.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("prod")
@Aspect
@Component
@AllArgsConstructor
public class SecuredRapidApiCallAspect {

    private final SecurityService securityService;

    @Around("@annotation(SecuredRapidApiCall)")
    public Object validateRapidApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof HttpServletRequest request) {
                securityService.validateRapidApiHeaders(request);
            }
        }

        return joinPoint.proceed();
    }
}
