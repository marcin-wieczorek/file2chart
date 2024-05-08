package com.file2chart.api.v1;

import com.file2chart.exceptions.ApiError;
import com.file2chart.model.dto.input.EmbeddedChartVisualizationRequest;
import com.file2chart.model.dto.input.ImageChartVisualizationRequest;
import com.file2chart.model.dto.output.VisualizationHashResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Chart")
@RequestMapping("/v1")
public interface ChartAPI {

    @Operation(description = "Generate hash from your data source based on file, visualization type and chart type.")
    @ApiResponse(responseCode = "200", description = "Visualization data", content = @Content(schema = @Schema(implementation = VisualizationHashResponse.class)))
    @ApiResponse(
            responseCode = "400",
            description = "Bad request.",
            content = @Content(schema = @Schema(implementation = ApiError.class),
                    examples = @ExampleObject(
                            name = "BAD_REQUEST",
                            value = """
                                    {
                                      "status": "BAD_REQUEST",
                                      "timestamp": "07-05-2024 09:30:00",
                                      "message": "Validation failed",
                                      "debugMessage": "Invalid input data"
                                    }
                                    """
                    )))
    @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden.", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "404", description = "Resource not found.", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "500", description = "The server encountered unexpected error.", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @PostMapping(value = "/chart/hash", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VisualizationHashResponse> generateChartHash(
            @Parameter(description = "File to upload, which will be the source for creating the chart. Only .csv is currently supported.") @RequestParam MultipartFile file
    );

    @Operation(description = "Generate visualisation as rendered HTML page.")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @ApiResponse(responseCode = "401", content = @Content)
    @ApiResponse(responseCode = "403", content = @Content)
    @ApiResponse(responseCode = "404", content = @Content)
    @PostMapping("/chart/visualization/embedded")
    String generateEmbeddedVisualization(@RequestBody EmbeddedChartVisualizationRequest input,
                                         Model model
    );

    @Operation(description = "Generate visualisation as Image (.png).")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", content = @Content)
    @ApiResponse(responseCode = "401", content = @Content)
    @ApiResponse(responseCode = "403", content = @Content)
    @ApiResponse(responseCode = "404", content = @Content)
    @PostMapping("/chart/visualization/image")
    ResponseEntity<InputStreamResource> generateImageVisualization(@RequestBody ImageChartVisualizationRequest input,
                                                                   Model model
    );
}
