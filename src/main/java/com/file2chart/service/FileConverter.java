package com.file2chart.service;

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

public abstract class FileConverter {

    @SneakyThrows
    public static CSVModel toCSV(InputStream inputStream, long limit) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        //settings.setNumberOfRecordsToRead(limit);

        CsvParser csvParser = new CsvParser(settings);
        List<Record> records = csvParser.parseAllRecords(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        List<String> headers = Arrays.stream(records.get(0).getValues())
                                     .toList();

        List<List<String>> cells = records.stream()
                                  .skip(1)
                                  .map(Record::getValues)
                                  .map(Arrays::asList)
                                  .collect(Collectors.toList());

        return new CSVModel(headers, cells);
    }

    public static ChartModel toChart(InputStream inputStream, long limit) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);

        CsvParser csvParser = new CsvParser(settings);
        List<Record> records = csvParser.parseAllRecords(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        Map<String, List<String>> resultMap = new HashMap<>();

        // Przetwarzamy nagłówki i wypełniamy mapę wynikową
        for (String header : records.get(0).getMetaData().headers()) {
            resultMap.put(header, new ArrayList<>());
        }

        // Wypełniamy mapę wartościami z rekordów
        for (Record record : records) {
            String[] values = record.getValues();
            List<String> headers = List.of(record.getMetaData().headers());
            for (int i = 0; i < values.length; i++) {
                String header = headers.get(i);
                resultMap.get(header).add(values[i]);
            }
        }

        return new ChartModel(resultMap);
    }
}
