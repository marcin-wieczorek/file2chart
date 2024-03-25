package com.file2chart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.file2chart.model.dto.local.CSVModel;
import com.file2chart.model.dto.output.TableOutput;
import com.file2chart.service.files.FileConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class TableService {
    private final ObjectMapper mapper;

    public TableOutput generateTable(MultipartFile file) throws IOException {
        CSVModel csvModel = FileConverter.toCSV(file.getInputStream(), 10l);
        return TableOutput.builder()
                          .headers(csvModel.getHeaders())
                          .cells(csvModel.getCells())
                          .build();
    }

}
