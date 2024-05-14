package com.file2chart.model.enums;

import lombok.Getter;

@Getter
public enum PricingPlan {
    BASIC(25, false),
    PRO(100, false),
    ULTRA(1000, true),
    MEGA(0, true);

    private int recordsLimit;
    private int requestsLimit;
    private boolean convertToImage;

    PricingPlan(int recordsLimit, boolean convertToImage) {
        this.recordsLimit = recordsLimit;
        this.convertToImage = convertToImage;
    }


}
