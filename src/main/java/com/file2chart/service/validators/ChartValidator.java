package com.file2chart.service.validators;

import com.file2chart.exceptions.InvalidHeaderException;
import com.file2chart.service.resolver.BaseFileResolver;
import com.univocity.parsers.common.record.Record;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Slf4j
public class ChartValidator {

    public static void validateCsvHeaders(List<Record> records) {
        if (records.size() == 0) {
            log.error("No records found.");
            throw new InvalidHeaderException("No records found.");
        }

        if (records.get(0).getMetaData() == null) {
            log.error("Metadata is missing for at least one record.");
            throw new InvalidHeaderException("Metadata is missing for at least one record.");
        }

        if (records.get(0).getMetaData().headers() == null || records.get(0).getMetaData().headers().length == 0) {
            log.error("No headers found in metadata for at least one record.");
            throw new InvalidHeaderException("No headers found in metadata for at least one record.");
        }

        try {
            List.of(records.get(0).getMetaData().headers())
                .stream()
                .filter(StringUtils::isEmptyOrWhitespace)
                .findFirst()
                .ifPresent(element -> {
                    log.error("One or more headers contains empty value.");
                    throw new InvalidHeaderException("One or more headers contains empty value.");
                });
        } catch (Exception e) {
            log.error("One or more headers contains null value.", e);
            throw new InvalidHeaderException("One or more headers contains null value.", e);
        }

    }

    public static void validateHeaders(List<String> records) {
        boolean containsDescriptionHeader = records.stream()
                                                   .anyMatch(header -> StringUtils.equalsIgnoreCase(
                                                           header,
                                                           BaseFileResolver.HEADER_DESCRIPTION));
        if (!containsDescriptionHeader) {
            log.error("Missing description column in the source data file. Please check if the file contains the required 'description' column.");
            throw new InvalidHeaderException("Missing description column in the source data file. Please check if the file contains the required 'description' column.");
        }
    }

}
