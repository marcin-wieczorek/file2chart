package com.file2chart.config.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LoggingService {

    void displayReq(HttpServletRequest request, Object body);

    void displayResp(HttpServletRequest request, HttpServletResponse response, Object body);
}
