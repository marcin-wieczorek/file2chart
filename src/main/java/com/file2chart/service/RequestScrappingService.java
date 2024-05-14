package com.file2chart.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestScrappingService {

    public Optional<String> getHeaderValue(HttpServletRequest request, String headerName) {
        return Optional.ofNullable(request.getHeader(headerName));
    }
}
