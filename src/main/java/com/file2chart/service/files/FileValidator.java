package com.file2chart.service.files;

import com.file2chart.model.enums.FileFormat;
import com.file2chart.service.utils.FileUtils;
import com.univocity.parsers.common.record.Record;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    public static void validateRecords(List<Record> records) {
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
    }

    public static void validateNumericValue(String value) {
        boolean isValid = isNumeric(value);

        if (!isValid) {
            log.error("One or more values contain non-numeric characters.");
            throw new RuntimeException("One or more values contain non-numeric characters.");
        }
    }

    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
