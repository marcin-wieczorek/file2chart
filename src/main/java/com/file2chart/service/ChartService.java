package com.file2chart.service;

import com.file2chart.model.dto.local.ChartModel;
import com.file2chart.model.dto.output.ChartOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class ChartService {

    public ChartOutput generateChart(MultipartFile file) throws IOException {
        ChartModel chartModel = FileConverter.toChart(file.getInputStream(), 10l);
        return ChartOutput.builder()
                          .map(chartModel.getMap())
                          .build();
    }

}
