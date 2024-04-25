package com.file2chart.service.validators;

import com.file2chart.model.enums.FileFormat;
import com.file2chart.service.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileValidator {

    public static void validateFileFormat(MultipartFile file) {
        String fileExtension = FileUtils.getFileExtension(file.getOriginalFilename());

        boolean isValidFormat = Arrays.stream(FileFormat.values())
                                      .anyMatch(fileFormat -> StringUtils.equalsIgnoreCase(fileExtension, fileFormat.toString()));

        if (!isValidFormat) {
            String supportedFormats = Arrays.stream(FileFormat.values())
                                            .map(Enum::name)
                                            .collect(Collectors.joining(", "));

            log.error("Invalid file format. { 'provided': '{}', 'supported': '{}' }", fileExtension, supportedFormats);
            throw new RuntimeException("Invalid file format. Supported formats: " + supportedFormats);
        }
    }



    public static void validateMapHeaders(List<String> headers) {
        List<String> normalizedHeaders = headers.stream().map(String::toLowerCase).toList();

        boolean hasLatitude = normalizedHeaders.stream().anyMatch(header -> isLatitudeHeader(header));
        boolean hasLongitude = normalizedHeaders.stream().anyMatch(header -> isLongitudeHeader(header));

        if (!hasLatitude || !hasLongitude) {
            log.error("The headers list does not contain the required columns for latitude (x, lat, latitude) or longitude (y, long, longitude).");
            throw new IllegalArgumentException("The headers list does not contain the required columns for latitude (x, lat, latitude) or longitude (y, long, longitude).");
        }
    }

    public static void validateNumericValue(String value) {
        boolean isValid = isNumeric(value);

        if (!isValid) {
            log.error("One or more values contain non-numeric characters.");
            throw new RuntimeException("One or more values contain non-numeric characters.");
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

    public static boolean isNumeric(String str) {
        return NumberUtils.isCreatable(str);
    }
}
