package com.file2chart.controller;

import com.file2chart.api.v1.TableAPI;
import com.file2chart.aspect.rapidapi.secure.SecuredRapidApiCall;
import com.file2chart.model.dto.input.EmbeddedTableVisualizationRequest;
import com.file2chart.model.dto.input.ImageTableVisualizationRequest;
import com.file2chart.model.dto.output.TableOutput;
import com.file2chart.model.dto.output.VisualizationHashResponse;
import com.file2chart.model.enums.PricingPlan;
import com.file2chart.service.security.SecurityService;
import com.file2chart.service.tools.ScreenCaptureTool;
import com.file2chart.service.visualization.TableService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final SecurityService securityService;

    @Override
    @SecuredRapidApiCall
    public ResponseEntity<VisualizationHashResponse> generateTableHash(MultipartFile file, HttpServletRequest request) {
        PricingPlan pricingPlan = securityService.getPricingPlan(request);
        TableOutput tableOutput = tableService.generateTableOutput(file, pricingPlan);
        String serializedData = tableService.serializeTable(tableOutput);

        VisualizationHashResponse visualizationHashResponse = VisualizationHashResponse.builder()
                                                                                       .hash(serializedData)
                                                                                       .build();

        return ResponseEntity.ok().body(visualizationHashResponse);
    }

    @Override
    @SecuredRapidApiCall
    public String generateEmbeddedTableVisualization(EmbeddedTableVisualizationRequest input, Model model, HttpServletRequest request) {
        model.addAttribute("data", tableService.deserializeTable(input.getHash()));
        return "table/index";
    }

    @Override
    @SecuredRapidApiCall
    public ResponseEntity<InputStreamResource> generateImageTableVisualization(ImageTableVisualizationRequest input, Model model, HttpServletRequest request) {
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
