package com.file2chart.service.validators;

import com.file2chart.exceptions.custom.UnsupportedPricingPlanException;
import com.file2chart.model.enums.PricingPlan;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PricingPlanValidator {

    public static void validateLimits(List<List<String>> records, PricingPlan pricingPlan) {
        if (!pricingPlan.equals(PricingPlan.ULTRA)) {
            if (records.size() > pricingPlan.getRecordsLimit()) {
                log.error("The current pricing plan ({}) allows up to {} records, but the number of records in the source exceeds this limit.", pricingPlan, pricingPlan.getRecordsLimit());
                throw new UnsupportedPricingPlanException("The current pricing plan (" + pricingPlan + ") allows up to " + pricingPlan.getRecordsLimit() + " records, but the number of records in the source exceeds this limit.");
            }
        }
    }
}
