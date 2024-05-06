package com.file2chart.service.validators;

import com.file2chart.exceptions.InvalidNumericValueException;
import com.file2chart.exceptions.InvalidStringValueException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

@Slf4j
public class CharValidator {

    public static void validateNumericValue(String value) {
        boolean isValid = isNumeric(value);

        if (!isValid) {
            log.error("One or more values contain non-numeric characters.");
            throw new InvalidNumericValueException("One or more values contain non-numeric characters.");
        }
    }

    public static void validateStringValue(String value) {
        boolean isValid = !isNumeric(value);

        if (!isValid) {
            log.error("One or more values contain strict-numeric characters.");
            throw new InvalidStringValueException("One or more values contain strict-numeric characters.");
        }
    }

    public static boolean isNumeric(String str) {
        return NumberUtils.isCreatable(str);
    }
}
