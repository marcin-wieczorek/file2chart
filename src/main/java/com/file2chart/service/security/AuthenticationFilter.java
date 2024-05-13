package com.file2chart.service.security;

import com.file2chart.exceptions.ApiErrorTranslator;
import com.file2chart.exceptions.custom.BadCredentialsException;
import com.file2chart.service.utils.JsonConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

@Profile("!dev")
@Component
@AllArgsConstructor
public class AuthenticationFilter extends GenericFilterBean {

    private final JsonConverter converter;
    private final ApiErrorTranslator apiErrorTranslator;
    private final ApiAuthenticationService authenticationService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {
        try {
            Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException ex) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            String json = converter.toJSON(apiErrorTranslator.getApiError(ex), false);
            writer.print(json);
            writer.flush();
            writer.close();
        }
    }

}
