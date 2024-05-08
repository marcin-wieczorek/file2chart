package com.file2chart.api.v1;

import com.file2chart.model.dto.input.EmbeddedTableVisualizationRequest;
import com.file2chart.model.dto.input.ImageTableVisualizationRequest;
import com.file2chart.model.dto.output.VisualizationHashResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Table")
@RequestMapping("/v1")
public interface TableAPI {

    @Operation(description = "Generate hash from your data source based on file, visualization type. It's needed to generate data visualization.")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @ApiResponse(responseCode = "401", content = @Content)
    @ApiResponse(responseCode = "403", content = @Content)
    @ApiResponse(responseCode = "404", content = @Content)
    @PostMapping("/table/hash")
    ResponseEntity<VisualizationHashResponse> generateTableHash(@RequestParam MultipartFile file);

    @Operation(description = "Generate visualisation as rendered HTML page.")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @ApiResponse(responseCode = "401", content = @Content)
    @ApiResponse(responseCode = "403", content = @Content)
    @ApiResponse(responseCode = "404", content = @Content)
    @PostMapping("/table/visualization/embedded")
    String generateEmbeddedVisualization(@RequestBody EmbeddedTableVisualizationRequest input, Model model);

    @Operation(description = "Generate visualisation as Image (.png).")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @ApiResponse(responseCode = "401", content = @Content)
    @ApiResponse(responseCode = "403", content = @Content)
    @ApiResponse(responseCode = "404", content = @Content)
    @PostMapping("/table/visualization/image")
    ResponseEntity<InputStreamResource> generateImageVisualization(@RequestBody ImageTableVisualizationRequest input, Model model);
}
