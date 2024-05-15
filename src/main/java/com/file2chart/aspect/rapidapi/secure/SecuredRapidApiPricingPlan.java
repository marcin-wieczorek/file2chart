package com.file2chart.aspect.rapidapi.secure;

import com.file2chart.model.enums.PricingPlan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecuredRapidApiPricingPlan {
    PricingPlan[] pricingPlans() default {};
}

