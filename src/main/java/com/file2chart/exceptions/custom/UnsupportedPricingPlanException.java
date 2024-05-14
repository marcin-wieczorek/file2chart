package com.file2chart.exceptions.custom;

import com.file2chart.exceptions.ErrorCode;
import com.file2chart.exceptions.http.HttpUnauthorizedException;

public class UnsupportedPricingPlanException extends HttpUnauthorizedException {
    private static ErrorCode code = ErrorCode.UNSUPPORTED_PRICING_PLAN;

    public UnsupportedPricingPlanException() {
        super();
    }

    public UnsupportedPricingPlanException(String message, Throwable cause) {
        super(code, message, cause);
    }

    public UnsupportedPricingPlanException(String message) {
        super(code, message);
    }

    public UnsupportedPricingPlanException(Throwable cause) {
        super(code, cause);
    }
}
