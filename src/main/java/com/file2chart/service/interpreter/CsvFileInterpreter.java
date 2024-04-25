package com.file2chart.service.interpreter;

import com.file2chart.model.dto.local.*;
import com.file2chart.model.enums.ChartType;
import com.file2chart.model.enums.FileFormat;
import com.file2chart.service.interpreter.chart.csv.CSVChartResolver;
import com.file2chart.service.validators.FileValidator;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CsvFileInterpreter implements FileInterpreter {

    private final List<CSVChartResolver> csvChartResolvers;

    private static final List<String> HEADERS_LATITUDE = Arrays.asList("x", "lat", "latitude");
    private static final List<String> HEADERS_LONGITUDE = Arrays.asList("y", "long", "longitude");

    @Override
    @SneakyThrows
    public ChartModel toChart(MultipartFile file, ChartType chartType) {
        CSVChartResolver CSVChartResolver = csvChartResolvers.stream()
                                                             .filter(resolver -> resolver.isSupported(chartType))
                                                             .findFirst()
                                                             .orElseThrow(null);

        ChartModel chartModel = CSVChartResolver.resolveModel(file);

        return chartModel;
    }





    @Override
    @SneakyThrows
    public MapModel toMap(MultipartFile file) {
        List<Record> records = getRecords(file.getInputStream());

        List<String> headers = List.of(records.get(0).getMetaData().headers());
        FileValidator.validateMapHeaders(headers);

        List<List<String>> cells = records.stream()
                                          .map(Record::getValues)
                                          .map(Arrays::asList)
                                          .collect(Collectors.toList());

        List<GeoLocation> geoLocations = new ArrayList<>();

        String latitudeHeader = findHeaderWithKeyword(headers, HEADERS_LATITUDE);
        String longitudeHeader = findHeaderWithKeyword(headers, HEADERS_LONGITUDE);

        List<String> otherHeaders = headers.stream()
                                           .filter(header -> !header.equalsIgnoreCase(latitudeHeader) && !header.equalsIgnoreCase(longitudeHeader))
                                           .collect(Collectors.toList());

        int latitudeIndex = headers.indexOf(latitudeHeader);
        int longitudeIndex = headers.indexOf(longitudeHeader);

        for (List<String> row : cells) {
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
    public TableModel toTable(MultipartFile file) {
        List<Record> records = getRecords(file.getInputStream());
        List<String> headers = List.of(records.get(0).getMetaData().headers());
        List<List<String>> cells = records.stream()
                                          .map(Record::getValues)
                                          .map(Arrays::asList)
                                          .collect(Collectors.toList());

        return new TableModel(headers, cells);
    }

    @Override
    public FileFormat getFileFormat() {
        return FileFormat.CSV;
    }

    private static List<Record> getRecords(InputStream inputStream) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        //settings.setNumberOfRecordsToRead(limit);

        CsvParser csvParser = new CsvParser(settings);
        List<Record> records = csvParser.parseAllRecords(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        return records;
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
