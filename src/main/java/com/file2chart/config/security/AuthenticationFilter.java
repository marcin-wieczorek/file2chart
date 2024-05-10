package com.file2chart.config.security;

import com.file2chart.exceptions.RestExceptionHandler;
import com.file2chart.exceptions.custom.BadCredentialsException;
import com.file2chart.service.utils.JsonConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFilter extends GenericFilterBean {

    private final JsonConverter converter;

    public AuthenticationFilter(JsonConverter converter) {
        this.converter = converter;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {
        try {
            Authentication authentication = ApiAuthenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException ex) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            String json = converter.toJSON(RestExceptionHandler.buildError(ex), false);
            writer.print(json);
            writer.flush();
            writer.close();
        }
    }

}
