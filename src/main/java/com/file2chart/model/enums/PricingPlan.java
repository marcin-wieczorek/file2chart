package com.file2chart.model.enums;

import lombok.Getter;

@Getter
public enum PricingPlan {
    BASIC(25, 2, false),
    PRO(100, 8, false),
    ULTRA(1000, 64, true),
    MEGA(0, 0, true);

    private int recordsLimit;
    private int datasetsLimit;
    private boolean convertToImage;

    PricingPlan(int recordsLimit, int datasetsLimit, boolean convertToImage) {
        this.recordsLimit = recordsLimit;
        this.datasetsLimit = datasetsLimit;
        this.convertToImage = convertToImage;
    }


}
