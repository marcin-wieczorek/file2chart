package com.file2chart.controller;

import com.file2chart.api.v1.MapAPI;
import com.file2chart.model.dto.input.EmbeddedMapVisualizationRequest;
import com.file2chart.model.dto.input.ImageMapVisualizationRequest;
import com.file2chart.model.dto.output.MapOutput;
import com.file2chart.model.dto.output.VisualizationHashResponse;
import com.file2chart.service.GoogleMapsClient;
import com.file2chart.service.visualization.MapService;
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
public class MapController implements MapAPI {

    private final MapService mapService;
    private final GoogleMapsClient googleMapsClient;

    @Override
    public ResponseEntity<VisualizationHashResponse> generateMapHash(MultipartFile file) {
        MapOutput mapOutput = mapService.generateMapOutput(file);
        String serializedData = mapService.serializeMap(mapOutput);

        VisualizationHashResponse visualizationHashResponse = VisualizationHashResponse.builder()
                                                                                       .hash(serializedData)
                                                                                       .build();

        return ResponseEntity.ok().body(visualizationHashResponse);
    }

    @Override
    public String generateEmbeddedVisualization(EmbeddedMapVisualizationRequest input, Model model) {
        model.addAttribute("data", mapService.deserializeMap(input.getHash()));
        model.addAttribute("googleMapsScript", googleMapsClient.getScript());
        return "map/index";
    }

    @Override
    public ResponseEntity<InputStreamResource> generateImageVisualization(ImageMapVisualizationRequest input, Model model) {
        InputStreamResource image = googleMapsClient.getImage(mapService.deserializeMap(input.getHash()));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "map_image" + "_" + System.currentTimeMillis() + ".png");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(image);
    }
}
