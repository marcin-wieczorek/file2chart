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
    @PostMapping("/chart/hash")
    ResponseEntity<VisualizationData> generateChartHash(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("visualizationType") VisualizationType visualizationType,
                                                        @RequestParam("chartType") ChartType chartType);

    @GetMapping("/chart/{chartType}/visualization/html")
    String generateHtmlVisualization(@RequestParam String hash, @PathVariable ChartType chartType, Model model);

    @GetMapping("/chart/{chartType}/visualization/image")
    String generateImageVisualization(@RequestParam String hash, @PathVariable ChartType chartType, Model model);
}
