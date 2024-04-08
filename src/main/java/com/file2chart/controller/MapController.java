package com.file2chart.controller;

import com.file2chart.api.v1.MapAPI;
import com.file2chart.model.dto.output.MapOutput;
import com.file2chart.model.dto.output.VisualizationData;
import com.file2chart.model.enums.VisualizationType;
import com.file2chart.service.GoogleMapsService;
import com.file2chart.service.MapService;
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
public class MapController implements MapAPI {

    private final MapService mapService;
    private final GoogleMapsService googleMapsService;

    @Override
    public ResponseEntity<VisualizationData> generateMapData(MultipartFile file, VisualizationType visualizationType) {
        FileValidator.validateFileFormat(file);

        MapOutput mapOutput = mapService.generateHtmlMapData(file);
        String serializedData = mapService.serializeMap(mapOutput);

        VisualizationData visualizationData = VisualizationData.builder()
                                                               .data(serializedData)
                                                               .path("/map/" + visualizationType.getType() + "/visualization")
                                                               .build();

        return ResponseEntity.ok().body(visualizationData);
    }

    @Override
    public String generateHtmlVisualization(String data, Model model) {
        model.addAttribute("data", mapService.deserializeMap(data));
        model.addAttribute("googleMapsScript", googleMapsService.getScript());
        return "map/index";
    }

    @Override
    public ResponseEntity<InputStreamResource> generateImageVisualization(String data, Model model) {
        InputStreamResource image = googleMapsService.getImage();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "map_image" + "_" + System.currentTimeMillis() + ".png");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(image);
    }
}
