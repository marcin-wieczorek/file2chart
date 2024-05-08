package com.file2chart.controller;

import com.file2chart.api.v1.TableAPI;
import com.file2chart.model.dto.input.EmbeddedTableVisualizationRequest;
import com.file2chart.model.dto.input.ImageTableVisualizationRequest;
import com.file2chart.model.dto.output.TableOutput;
import com.file2chart.model.dto.output.VisualizationHashResponse;
import com.file2chart.service.tools.ScreenCaptureTool;
import com.file2chart.service.visualization.TableService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class TableController implements TableAPI {

    private final TableService tableService;
    private final ScreenCaptureTool screenCaptureTool;

    @Override
    public ResponseEntity<VisualizationHashResponse> generateTableHash(MultipartFile file) {
        TableOutput tableOutput = tableService.generateTableOutput(file);
        String serializedData = tableService.serializeTable(tableOutput);

        VisualizationHashResponse visualizationHashResponse = VisualizationHashResponse.builder()
                                                                                       .hash(serializedData)
                                                                                       .build();

        return ResponseEntity.ok().body(visualizationHashResponse);
    }

    @Override
    public String generateEmbeddedVisualization(EmbeddedTableVisualizationRequest input, Model model) {
        model.addAttribute("data", tableService.deserializeTable(input.getHash()));
        return "table/index";
    }

    @Override
    public ResponseEntity<InputStreamResource> generateImageVisualization(ImageTableVisualizationRequest input, Model model) {
        model.addAttribute("data", tableService.deserializeTable(input.getHash()));
        InputStreamResource inputStreamResource = screenCaptureTool.captureScreen(model, "table/index");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "table_image" + "_" + System.currentTimeMillis() + ".png");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(inputStreamResource);
    }
}
