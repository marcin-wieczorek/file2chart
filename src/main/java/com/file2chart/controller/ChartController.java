package com.file2chart.controller;

import com.file2chart.api.v1.ChartAPI;
import com.file2chart.aspect.SecuredRapidApiCall;
import com.file2chart.model.dto.input.EmbeddedChartVisualizationRequest;
import com.file2chart.model.dto.input.ImageChartVisualizationRequest;
import com.file2chart.model.dto.output.ChartOutput;
import com.file2chart.model.dto.output.VisualizationHashResponse;
import com.file2chart.service.tools.ScreenCaptureTool;
import com.file2chart.service.visualization.ChartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class ChartController implements ChartAPI {

    private final ChartService chartService;
    private final ScreenCaptureTool screenCaptureTool;

    @Override
    @SecuredRapidApiCall
    public ResponseEntity<VisualizationHashResponse> generateChartHash(MultipartFile file, HttpServletRequest request) {
        ChartOutput chartOutput = chartService.generateChartOutput(file);
        String serializedData = chartService.serializeMap(chartOutput);

        VisualizationHashResponse visualizationHashResponse = VisualizationHashResponse.builder()
                                                                                       .hash(serializedData)
                                                                                       .build();

        return ResponseEntity.ok().body(visualizationHashResponse);
    }

    @Override
    public String generateEmbeddedVisualization(EmbeddedChartVisualizationRequest input, Model model, HttpServletRequest request) {
        model.addAttribute("data", chartService.deserializeMap(input.getHash()));
        return "chart/" + input.getChartType().getType() + "/index";
    }

    @Override
    public ResponseEntity<InputStreamResource> generateImageVisualization(
            ImageChartVisualizationRequest input, Model model, HttpServletRequest request) {
        model.addAttribute("data", chartService.deserializeMap(input.getHash()));

        InputStreamResource inputStreamResource = screenCaptureTool.captureScreen(model, "chart/" + input.getChartType().getType() + "/index");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "chart_image" + "_" + System.currentTimeMillis() + ".png");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(inputStreamResource);
    }
}
