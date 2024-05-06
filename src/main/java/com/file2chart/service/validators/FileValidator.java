package com.file2chart.service.validators;

import com.file2chart.exceptions.InvalidFileFormatException;
import com.file2chart.model.enums.FileFormat;
import com.file2chart.service.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
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
            throw new InvalidFileFormatException("Invalid file format. Supported formats: " + supportedFormats);
        }
    }

}
