package com.file2chart.controller;

import com.file2chart.api.v1.ChartAPI;
import com.file2chart.model.dto.output.ChartOutput;
import com.file2chart.model.dto.output.VisualizationData;
import com.file2chart.model.enums.ChartType;
import com.file2chart.model.enums.VisualizationType;
import com.file2chart.service.visualization.ChartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ChartController implements ChartAPI {

    private final ChartService chartService;

    @Override
    public ResponseEntity<VisualizationData> generateChartHash(MultipartFile file, VisualizationType visualizationType, ChartType chartType) {
        ChartOutput chartOutput = chartService.generateChart(file, chartType);
        String serializedData = chartService.serializeMap(chartOutput);

        VisualizationData visualizationData = VisualizationData.builder()
                                                               .data(serializedData)
                                                               .path("/chart/" + chartType.getType() + "/visualization/" + visualizationType.getType())
                                                               .build();

        return ResponseEntity.ok().body(visualizationData);
    }

    @Override
    public String generateHtmlVisualization(String hash, ChartType chartType, Model model) {
        model.addAttribute("data", chartService.deserializeMap(hash));
        return "chart/" + chartType.getType() + "/index";
    }

    @Override
    public String generateImageVisualization(String hash, ChartType chartType, Model model) {
        return "";
    }
}
