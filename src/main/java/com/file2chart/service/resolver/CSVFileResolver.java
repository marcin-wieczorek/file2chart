package com.file2chart.service.resolver;

import com.file2chart.exceptions.custom.FileParsingException;
import com.file2chart.exceptions.custom.InvalidHeaderException;
import com.file2chart.service.validators.ChartValidator;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.common.record.RecordMetaData;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CSVFileResolver extends BaseFileResolver<List<Record>> {

    @Override
    public List<Record> getObject(MultipartFile file) {
        try {
            CsvParserSettings settings = new CsvParserSettings();
            settings.setHeaderExtractionEnabled(true);
            settings.setDelimiterDetectionEnabled(true);
            //settings.setNumberOfRecordsToRead(limit);

            CsvParser csvParser = new CsvParser(settings);
            List<Record> records = csvParser.parseAllRecords(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            return records;
        } catch (IOException e) {
            throw new FileParsingException("Error occurred while parsing CSV file", e);
        }
    }

    @Override
    public List<String> getHeaders(List<Record> records) {
        ChartValidator.validateCsvHeaders(records);
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

}
