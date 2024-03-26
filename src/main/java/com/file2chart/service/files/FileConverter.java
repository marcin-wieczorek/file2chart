package com.file2chart.service.files;

import com.file2chart.model.dto.local.CSVModel;
import com.file2chart.model.dto.local.ChartModel;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class FileConverter {

    @SneakyThrows
    public static CSVModel toCSV(InputStream inputStream, long limit) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        //settings.setNumberOfRecordsToRead(limit);

        CsvParser csvParser = new CsvParser(settings);
        List<Record> records = csvParser.parseAllRecords(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        FileValidator.validateRecords(records);

        List<String> headers = List.of(records.get(0).getMetaData().headers());

        List<List<String>> cells = records.stream()
                                  .map(Record::getValues)
                                  .map(Arrays::asList)
                                  .collect(Collectors.toList());

        return new CSVModel(headers, cells);
    }

    public static ChartModel toChart(InputStream inputStream, long limit) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        //settings.setNumberOfRecordsToRead(limit);

        CsvParser csvParser = new CsvParser(settings);
        List<Record> records = csvParser.parseAllRecords(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        FileValidator.validateRecords(records);

        Map<String, List<String>> resultMap = Collections.unmodifiableMap(
                List.of(records.get(0).getMetaData().headers())
                    .stream()
                    .collect(Collectors.toMap(header -> header, header -> new ArrayList<>()))
        );

        records.forEach(record -> {
            String[] values = record.getValues();
            List<String> headers = resultMap.keySet().stream().toList();

            IntStream.range(0, values.length)
                     .forEach(value -> {
                         String header = headers.get(value);
                         FileValidator.validateNumericValue(values[value]);
                         resultMap.get(header).add(values[value]);
                     });
        });

        return new ChartModel(resultMap);
    }
}
