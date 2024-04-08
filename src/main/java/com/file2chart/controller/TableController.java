package com.file2chart.controller;

import com.file2chart.api.v1.TableAPI;
import com.file2chart.model.dto.output.TableOutput;
import com.file2chart.model.dto.output.VisualizationData;
import com.file2chart.model.enums.VisualizationType;
import com.file2chart.service.ImageGeneratorService;
import com.file2chart.service.TableService;
import com.file2chart.service.files.FileValidator;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TableController implements TableAPI {

    private final TableService tableService;
    private final ImageGeneratorService imageGeneratorService;

    @Override
    public ResponseEntity<VisualizationData> generateTableData(MultipartFile file, VisualizationType type) {
        FileValidator.validateFileFormat(file);

        TableOutput tableOutput = tableService.generateHtmlTableData(file);
        String serializedData = tableService.serializeTable(tableOutput);

        VisualizationData visualizationData = VisualizationData.builder()
                                                               .data(serializedData)
                                                               .path("/table/" + type.getType() + "/visualization")
                                                               .build();

        return ResponseEntity.ok().body(visualizationData);
    }

    @Override
    public String generateHtmlVisualization(String data, Model model) {
        model.addAttribute("data", tableService.deserializeTable(data));
        return "table/index";
    }

    @Override
    public ResponseEntity<InputStreamResource> generateImageVisualization(String data, Model model) {
        model.addAttribute("data", tableService.deserializeTable(data));
        InputStreamResource inputStreamResource = imageGeneratorService.generateImage(model, "table/index");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "table_image" + "_" + System.currentTimeMillis() + ".png");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(inputStreamResource);
    }
}
