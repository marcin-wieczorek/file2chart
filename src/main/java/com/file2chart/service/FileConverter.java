package com.file2chart.service;

import com.file2chart.model.dto.local.CsvModel;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FileConverter {

    @SneakyThrows
    public static CsvModel toCSV(InputStream inputStream, long limit) {
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

        return new CsvModel(headers, cells);
    }

    @SneakyThrows
    public static JSONObject toJSON(InputStream inputStream) {
        String jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        return new JSONObject(jsonString);
    }
}
