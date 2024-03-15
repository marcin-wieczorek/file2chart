package com.file2chart.service;

import com.file2chart.model.enums.FileFormat;
import com.file2chart.service.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class FileFormatValidator {

    public static void validate(MultipartFile file) {
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
}
