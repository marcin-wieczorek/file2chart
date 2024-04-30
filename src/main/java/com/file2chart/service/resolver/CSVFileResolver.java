package com.file2chart.service.resolver;

import com.file2chart.model.dto.local.*;
import com.file2chart.model.enums.ChartType;
import com.file2chart.service.validators.FileValidator;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CSVFileResolver implements FileResolver<List<Record>> {

    private static final String HEADER_DESCRIPTION = "description";
    private static final List<String> HEADERS_LATITUDE = Arrays.asList("x", "lat", "latitude");
    private static final List<String> HEADERS_LONGITUDE = Arrays.asList("y", "long", "longitude");

    public ChartModel resolveChartModel (MultipartFile file, ChartType chartType) {
        List<Record> object = getObject(file);

        List<String> headers = getHeaders(object);
        List<List<String>> records = getRecords(object);

        return transformToModel(headers, records);
    }

    public TableModel resolveTableModel (MultipartFile file) {
        List<Record> object = getObject(file);

        List<String> headers = getHeaders(object);
        List<List<String>> records = getRecords(object);

        return new TableModel(headers, records);
    }

    public MapModel resolveMapModel (MultipartFile file) {
        List<Record> object = getObject(file);

        List<String> headers = getHeaders(object);
        List<List<String>> records = getRecords(object);

        FileValidator.validateMapHeaders(headers);


        List<GeoLocation> geoLocations = new ArrayList<>();

        String latitudeHeader = findHeaderWithKeyword(headers, HEADERS_LATITUDE);
        String longitudeHeader = findHeaderWithKeyword(headers, HEADERS_LONGITUDE);

        List<String> otherHeaders = headers.stream()
                                           .filter(header -> !header.equalsIgnoreCase(latitudeHeader) && !header.equalsIgnoreCase(longitudeHeader))
                                           .collect(Collectors.toList());

        int latitudeIndex = headers.indexOf(latitudeHeader);
        int longitudeIndex = headers.indexOf(longitudeHeader);

        for (List<String> row : records) {
            Coordinate coordinate = new Coordinate(row.get(latitudeIndex), row.get(longitudeIndex));

            StringBuilder descriptionBuilder = new StringBuilder();
            for (String header : otherHeaders) {
                int index = headers.indexOf(header);
                descriptionBuilder.append(row.get(index)).append("; ");
            }

            String description = descriptionBuilder.toString();
            geoLocations.add(new GeoLocation(coordinate, description));
        }

        return MapModel.builder()
                       .geoLocations(geoLocations)
                       .build();
    }

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

        boolean containsDescriptionHeader = List.of(records.get(0).getMetaData().headers())
                                                .stream()
                                                .anyMatch(header -> !StringUtils.equalsIgnoreCase(header, HEADER_DESCRIPTION));

        if (!containsDescriptionHeader) {
            log.error("Missing description column in the source data file. Please check if the file contains the required description column.");
            throw new RuntimeException("Missing description column in the source data file. Please check if the file contains the required description column.");
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

    private static ChartModel transformToModel(List<String> headers, List<List<String>> records) {
        LinkedHashMap<String, List<String>> datasets = headers.stream()
                                                              .collect(Collectors.toMap(
                                                                      header -> header,
                                                                      header -> new ArrayList<>(),
                                                                      (a, b) -> b,
                                                                      LinkedHashMap::new));

        List<String> description = new ArrayList<>();

        for (List<String> record : records) {
            for (int i = 0; i < record.size(); i++) {
                String headerElement = headers.get(i);
                String recordValueElement = record.get(i);

                if (StringUtils.equals(headerElement, HEADER_DESCRIPTION)) {
                    FileValidator.validateStringValue(recordValueElement);
                    description.add(recordValueElement == null ? "" : recordValueElement);
                } else {
                    FileValidator.validateNumericValue(recordValueElement);
                    datasets.get(headerElement)
                            .add(recordValueElement == null ? "0" : recordValueElement);
                }
            }
        }
        datasets.remove(HEADER_DESCRIPTION);

        return new ChartModel(datasets, description);
    }

    private static String findHeaderWithKeyword(List<String> headers, List<String> keywords) {
        for (String keyword : keywords) {
            for (String header : headers) {
                if (header.equalsIgnoreCase(keyword.toLowerCase())) {
                    return header;
                }
            }
        }
        log.error("Keyword header not found.  { 'keywords': '{}' }", keywords);
        throw new IllegalArgumentException("Keyword header not found.");
    }

}
