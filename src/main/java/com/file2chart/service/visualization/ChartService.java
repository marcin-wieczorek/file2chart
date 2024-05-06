package com.file2chart.service.visualization;

import com.file2chart.model.dto.local.ChartModel;
import com.file2chart.model.dto.output.ChartOutput;
import com.file2chart.model.enums.ChartType;
import com.file2chart.service.compression.SecureDataProcessorService;
import com.file2chart.service.interpreter.FileInterpreter;
import com.file2chart.service.interpreter.FileInterpreterLoader;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class ChartService {

    private final FileInterpreterLoader interpreterLoader;
    private final SecureDataProcessorService secureDataProcessorService;

    @SneakyThrows
    public ChartOutput generateChartOutput(MultipartFile file, ChartType chartType) {
        FileInterpreter fileInterpreter = interpreterLoader.loadInterpreter(file);
        ChartModel chartModel = fileInterpreter.toChart(file, chartType);
        return ChartOutput.builder()
                          .map(chartModel.getMap())
                          .description(chartModel.getDescription())
                          .build();
    }

    public String serializeMap(ChartOutput chart) {
        return secureDataProcessorService.serialize(chart);
    }

    public ChartOutput deserializeMap(String data) {
        return secureDataProcessorService.deserialize(data, ChartOutput.class);
    }

}
