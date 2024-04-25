package com.file2chart.service.interpreter;

import com.file2chart.service.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileInterpreterLoader {

    private final List<FileInterpreter> interpreters;

    public FileInterpreter loadInterpreter(MultipartFile file) {
        return interpreters.stream()
                           .filter(interpreter -> FileUtils.getFileExtension(file.getOriginalFilename())
                                                           .equalsIgnoreCase(interpreter.getFileFormat().getFormat()))
                           .findFirst()
                           .orElseThrow(() -> {
                               log.error("Interpreter not found for this file format. {'extension': '{}'}", FileUtils.getFileExtension(file.getOriginalFilename()));
                               return new RuntimeException("Interpreter not found for this file format");
                });
    }
}
