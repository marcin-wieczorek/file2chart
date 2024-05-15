package com.file2chart.model.enums;

import lombok.Getter;

@Getter
public enum PricingPlan {
    BASIC(25),
    PRO(100),
    ULTRA(1000),
    MEGA(0);

    private int recordsLimit;

    PricingPlan(int recordsLimit) {
        this.recordsLimit = recordsLimit;
    }


}
