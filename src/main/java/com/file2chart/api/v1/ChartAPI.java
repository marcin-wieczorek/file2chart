package com.file2chart.api.v1;

import com.file2chart.model.dto.output.VisualizationData;
import com.file2chart.model.enums.ChartType;
import com.file2chart.model.enums.VisualizationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Chart")
public interface ChartAPI {

    @Operation(description = "Generate hash from your data source based on file, visualization type and chart type. It's needed to generate data visualization.")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @ApiResponse(responseCode = "401", content = @Content)
    @ApiResponse(responseCode = "403", content = @Content)
    @ApiResponse(responseCode = "404", content = @Content)
    @PostMapping("/chart/hash")
    ResponseEntity<VisualizationData> generateChartHash(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("visualizationType") VisualizationType visualizationType,
                                                        @RequestParam("chartType") ChartType chartType);

    @Operation(description = "Generate visualisation as rendered HTML page.")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @ApiResponse(responseCode = "401", content = @Content)
    @ApiResponse(responseCode = "403", content = @Content)
    @ApiResponse(responseCode = "404", content = @Content)
    @GetMapping("/chart/{chartType}/visualization/embedded")
    String generateEmbeddedVisualization(@RequestParam String hash, @PathVariable ChartType chartType, Model model);

    @Operation(description = "Generate visualisation as Image (.png).")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @ApiResponse(responseCode = "401", content = @Content)
    @ApiResponse(responseCode = "403", content = @Content)
    @ApiResponse(responseCode = "404", content = @Content)
    @GetMapping("/chart/{chartType}/visualization/image")
    ResponseEntity<InputStreamResource> generateImageVisualization(@RequestParam String hash, @PathVariable ChartType chartType, Model model);
}
