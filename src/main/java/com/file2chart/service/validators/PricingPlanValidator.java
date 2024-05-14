package com.file2chart.service.validators;

import com.file2chart.exceptions.custom.UnsupportedPricingPlanException;
import com.file2chart.model.enums.PricingPlan;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PricingPlanValidator {

    public static void validateLimits(List<List<String>> records, PricingPlan planPricing) {
        if (!planPricing.equals(PricingPlan.ULTRA)) {
            if (records.size() > planPricing.getRecordsLimit()) {
                log.error("The current pricing plan ({}) allows up to {} records, but the number of records in the source exceeds this limit.", planPricing, planPricing.getRecordsLimit());
                throw new UnsupportedPricingPlanException("The current pricing plan (" + planPricing + ") allows up to " + planPricing.getRecordsLimit() + " records, but the number of records in the source exceeds this limit.");
            }
            if (records.get(0).size() > planPricing.getDatasetsLimit()) {
                log.error("The current pricing plan ({}) allows up to {} records, but the number of datasets in the source exceeds this limit.", planPricing, planPricing.getDatasetsLimit());
                throw new UnsupportedPricingPlanException("The current pricing plan (" + planPricing + ") allows up to " + planPricing.getDatasetsLimit() + " datasets, but the number of datasets in the source exceeds this limit.");
            }
        }
    }
}
