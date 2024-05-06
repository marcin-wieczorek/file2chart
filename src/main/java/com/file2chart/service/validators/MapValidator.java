package com.file2chart.service.validators;

import com.file2chart.exceptions.InvalidHeaderException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MapValidator {

    public static void validateHeaders(List<String> headers) {
        List<String> normalizedHeaders = headers.stream().map(String::toLowerCase).toList();

        boolean hasLatitude = normalizedHeaders.stream().anyMatch(header -> isLatitudeHeader(header));
        boolean hasLongitude = normalizedHeaders.stream().anyMatch(header -> isLongitudeHeader(header));

        if (!hasLatitude || !hasLongitude) {
            log.error("The headers list does not contain the required columns for latitude (x, lat, latitude) or longitude (y, long, longitude).");
            throw new InvalidHeaderException("The headers list does not contain the required columns for latitude (x, lat, latitude) or longitude (y, long, longitude).");
        }
    }

    public static boolean isCoordinateHeader(String header) {
        return isLatitudeHeader(header) || isLongitudeHeader(header);
    }

    public static boolean isLatitudeHeader(String header) {
        return header.contains("x") || header.contains("lat") || header.contains("latitude");
    }

    public static boolean isLongitudeHeader(String header) {
        return header.contains("y") || header.contains("long") || header.contains("longitude");
    }

}
