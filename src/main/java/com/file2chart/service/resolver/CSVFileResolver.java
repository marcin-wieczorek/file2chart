package com.file2chart.service.resolver;

import com.file2chart.exceptions.InvalidHeaderException;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.common.record.RecordMetaData;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CSVFileResolver extends BaseFileResolver<List<Record>> {

    @Override
    @SneakyThrows
    public List<Record> getObject(MultipartFile file) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        //settings.setNumberOfRecordsToRead(limit);

        CsvParser csvParser = new CsvParser(settings);
        List<Record> records = csvParser.parseAllRecords(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
        return records;
    }

    @Override
    public List<String> getHeaders(List<Record> records) {
        validateHeaders(records);

        return records.stream()
                                  .findFirst()
                                  .map(Record::getMetaData)
                                  .map(RecordMetaData::headers)
                                  .map(List::of)
                                  .orElseThrow(() -> {
                                      log.error("One or more headers contains empty or null value.");
                                      throw new InvalidHeaderException("One or more headers contains empty or null value.");
                                  });
    }

    @Override
    public List<List<String>> getRecords(List<Record> records) {
        return records.stream()
                         .map(Record::getValues)
                         .map(Arrays::asList)
                         .collect(Collectors.toList());
    }

    @Override
    public List<String> getDescriptions(List<Record> object) {
        return List.of();
    }

    @Override
    public void validate(List<Record> object) {
        validateHeaders(object);
    }

    public static void validateHeaders(List<Record> records) {
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

        boolean containsDescriptionHeader = List.of(records.get(0).getMetaData().headers())
                                                .stream()
                                                .anyMatch(header -> StringUtils.equalsIgnoreCase(header, BaseFileResolver.HEADER_DESCRIPTION));

        if (!containsDescriptionHeader) {
            log.error("Missing description column in the source data file. Please check if the file contains the required description column.");
            throw new InvalidHeaderException("Missing description column in the source data file. Please check if the file contains the required description column.");
        }
    }

}
