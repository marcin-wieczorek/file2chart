package com.file2chart.service.resolver;

import com.file2chart.exceptions.custom.InvalidHeaderException;
import com.file2chart.model.dto.local.*;
import com.file2chart.model.enums.ChartType;
import com.file2chart.service.validators.CharValidator;
import com.file2chart.service.validators.ChartValidator;
import com.file2chart.service.validators.MapValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public abstract class BaseFileResolver<T> implements FileResolver<T> {

    public static final String HEADER_DESCRIPTION = "description";
    public static final List<String> HEADERS_LATITUDE = Arrays.asList("x", "lat", "latitude");
    public static final List<String> HEADERS_LONGITUDE = Arrays.asList("y", "long", "longitude");

    public ChartModel resolveChartModel (MultipartFile file, ChartType chartType) {
        T object = getObject(file);

        List<String> headers = getHeaders(object);
        ChartValidator.validateHeaders(headers);

        List<List<String>> records = getRecords(object);

        LinkedHashMap<String, List<String>> datasets = headers.stream()
                                                              .collect(Collectors.toMap(
                                                                      header -> header,
                                                                      header -> new ArrayList<>(),
                                                                      (a, b) -> b,
                                                                      LinkedHashMap::new));

        List<String> description = new ArrayList<>();
        String descriptionHeader = null;

        for (List<String> record : records) {
            for (int i = 0; i < record.size(); i++) {
                String headerElement = headers.get(i);
                String recordValueElement = record.get(i);

                if (StringUtils.equalsIgnoreCase(headerElement, HEADER_DESCRIPTION)) {
                    descriptionHeader = headerElement;
                    CharValidator.validateStringValue(recordValueElement);
                    description.add(recordValueElement == null ? "" : recordValueElement);
                } else {
                    recordValueElement = recordValueElement.replace(',', '.'); // Zamiana przecinka na kropkę
                    CharValidator.validateNumericValue(recordValueElement);
                    datasets.get(headerElement)
                            .add(recordValueElement == null ? "0" : recordValueElement);
                }
            }
        }

        if (descriptionHeader != null) {
            datasets.remove(descriptionHeader);
        }

        return new ChartModel(datasets, description);
    }

    public TableModel resolveTableModel (MultipartFile file) {
        T object = getObject(file);

        List<String> headers = getHeaders(object);
        List<List<String>> records = getRecords(object);

        return new TableModel(headers, records);
    }

    public MapModel resolveMapModel (MultipartFile file) {
        T object = getObject(file);

        List<String> headers = getHeaders(object);
        MapValidator.validateHeaders(headers);

        List<List<String>> records = getRecords(object);

        MapValidator.validateHeaders(headers);

        List<GeoLocation> geoLocations = new ArrayList<>();

        String latitudeHeader = findHeaderWithKeyword(headers, HEADERS_LATITUDE);
        String longitudeHeader = findHeaderWithKeyword(headers, HEADERS_LONGITUDE);

        String description = headers.stream()
                                    .filter(header -> header.equalsIgnoreCase(HEADER_DESCRIPTION))
                                    .findFirst()
                                    .orElse("");

        int latitudeIndex = headers.indexOf(latitudeHeader);
        int longitudeIndex = headers.indexOf(longitudeHeader);

        for (List<String> row : records) {
            String latitudeValue = row.get(latitudeIndex);
            CharValidator.validateNumericValue(latitudeValue);

            String longitudeValue = row.get(longitudeIndex);
            CharValidator.validateNumericValue(longitudeValue);

            Coordinate coordinate = new Coordinate(latitudeValue, longitudeValue);
            geoLocations.add(new GeoLocation(coordinate, description));
        }

        return MapModel.builder()
                       .geoLocations(geoLocations)
                       .build();
    }

    @Override
    public abstract T getObject(MultipartFile file);

    @Override
    public abstract List<String> getHeaders(T object);

    @Override
    public abstract List<List<String>> getRecords(T object);

    @Override
    public List<String> getDescriptions(T object) {
        return List.of();
    }

    private static String findHeaderWithKeyword(List<String> headers, List<String> keywords) {
        for (String keyword : keywords) {
            for (String header : headers) {
                if (header.equalsIgnoreCase(keyword.toLowerCase())) {
                    return header;
                }
            }
        }
        log.error("Keyword header not found. { 'keywords': '{}' }", keywords);
        throw new InvalidHeaderException("Keyword header not found. Keywords: " + keywords);
    }

}
