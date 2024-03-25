package com.file2chart.service;

import com.file2chart.model.dto.local.CSVModel;
import com.file2chart.model.dto.output.TableOutput;
import com.file2chart.service.files.FileConverter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class MapService {

    @SneakyThrows
    public TableOutput generateMap(MultipartFile file) {
        CSVModel csvModel = FileConverter.toCSV(file.getInputStream(), 10l);
        return TableOutput.builder()
                          .headers(csvModel.getHeaders())
                          .cells(csvModel.getCells())
                          .build();
    }

}
