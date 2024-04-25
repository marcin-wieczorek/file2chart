package com.file2chart.service.interpreter.chart.csv;

import com.file2chart.model.dto.local.ChartModel;
import com.file2chart.model.enums.ChartType;
import com.file2chart.service.interpreter.chart.ChartResolver;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.common.record.RecordMetaData;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class CSVChartResolver implements ChartResolver<List<Record>> {
    public abstract ChartModel resolveModel(MultipartFile file);

    public abstract boolean isSupported(ChartType chartType);

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
                                      throw new RuntimeException("One or more headers contains empty or null value.");
                                  });
    }

    @Override
    public List<List<String>> getRecords(List<Record> records) {
        return records.stream()
                         .map(Record::getValues)
                         .map(Arrays::asList)
                         .collect(Collectors.toList());
    }

    public static void validateHeaders(List<Record> records) {
        if (records.size() == 0) {
            log.error("No records found.");
            throw new RuntimeException("No records found.");
        }

        if (records.get(0).getMetaData() == null) {
            log.error("Metadata is missing for at least one record.");
            throw new RuntimeException("Metadata is missing for at least one record.");
        }

        if (records.get(0).getMetaData().headers() == null || records.get(0).getMetaData().headers().length == 0) {
            log.error("No headers found in metadata for at least one record.");
            throw new RuntimeException("No headers found in metadata for at least one record.");
        }

        try {
            List.of(records.get(0).getMetaData().headers())
                .stream()
                .filter(StringUtils::isEmptyOrWhitespace)
                .findFirst()
                .ifPresent(element -> {
                    log.error("One or more headers contains empty value.");
                    throw new RuntimeException("One or more headers contains empty value.");
                });
        } catch (Exception e) {
            log.error("One or more headers contains null value.", e);
            throw new RuntimeException("One or more headers contains null value.", e);
        }
    }

    private static void validateRecords(List<Record> records) {
        int firstRecordLength = records.get(0).getValues().length;
        boolean allRecordsSameLength = records.stream()
                                              .skip(1)
                                              .allMatch(l -> l.getValues().length == firstRecordLength);

        if (!allRecordsSameLength) {
            log.error("No same record values length for at least one record.");
            throw new RuntimeException("No same record values length for at least one record.");
        }
    }

}
