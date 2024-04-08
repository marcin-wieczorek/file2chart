package com.file2chart.api.v1;

import com.file2chart.model.dto.output.VisualizationData;
import com.file2chart.model.enums.ChartType;
import com.file2chart.model.enums.VisualizationType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ChartAPI {
    @PostMapping("/chart/data")
    ResponseEntity<VisualizationData> generateChartData(@RequestParam MultipartFile file,
                                                        @RequestParam VisualizationType visualizationType,
                                                        @RequestParam ChartType chartType);

    @GetMapping("/chart/visualization/html")
    String generateHtmlVisualization(@PathVariable String data, @PathVariable ChartType chartType, Model model);

    @GetMapping("/chart/visualization/image")
    String generateImageVisualization(@PathVariable String data, @PathVariable ChartType chartType, Model model);
}
