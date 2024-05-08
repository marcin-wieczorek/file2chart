package com.file2chart.service.interpreter;

import com.file2chart.exceptions.custom.InterpreterNotFoundException;
import com.file2chart.model.enums.FileFormat;
import com.file2chart.service.utils.FileUtils;
import com.file2chart.service.validators.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileInterpreterLoader {

    private final List<FileInterpreter> interpreters;

    public FileInterpreter loadInterpreter(MultipartFile file) {
        FileValidator.validateFileFormat(file);

        return interpreters.stream()
                           .filter(interpreter -> {
                               String fileExtension = FileUtils.getFileExtension(file.getOriginalFilename());
                               return Arrays.stream(FileFormat.values())
                                            .anyMatch(format -> format.getFormat().equalsIgnoreCase(fileExtension));
                           })
                           .findFirst()
                           .orElseThrow(() -> {
                               log.error("Interpreter not found for this file format. {'extension': '{}'}", FileUtils.getFileExtension(file.getOriginalFilename()));
                               return new InterpreterNotFoundException("Interpreter not found for this file format");
                });
    }
}
