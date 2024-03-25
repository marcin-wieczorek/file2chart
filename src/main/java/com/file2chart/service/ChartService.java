package com.file2chart.service;

import com.file2chart.model.dto.local.ChartModel;
import com.file2chart.model.dto.output.ChartOutput;
import com.file2chart.service.files.FileConverter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class ChartService {

    @SneakyThrows
    public ChartOutput generateChart(MultipartFile file) {
        ChartModel chartModel = FileConverter.toChart(file.getInputStream(), 10l);
        return ChartOutput.builder()
                          .map(chartModel.getMap())
                          .build();
    }

}
