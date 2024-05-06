package com.file2chart.controller;

import com.file2chart.api.v1.MapAPI;
import com.file2chart.model.dto.output.MapOutput;
import com.file2chart.model.dto.output.VisualizationData;
import com.file2chart.model.enums.VisualizationType;
import com.file2chart.service.GoogleMapsClient;
import com.file2chart.service.visualization.MapService;
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
    private final GoogleMapsClient googleMapsClient;

    @Override
    public ResponseEntity<VisualizationData> generateMapHash(MultipartFile file, VisualizationType visualizationType) {
        MapOutput mapOutput = mapService.generateHtmlMapData(file);
        String serializedData = mapService.serializeMap(mapOutput);

        VisualizationData visualizationData = VisualizationData.builder()
                                                               .hash(serializedData)
                                                               .path("/map/visualization/" + visualizationType.getType())
                                                               .build();

        return ResponseEntity.ok().body(visualizationData);
    }

    @Override
    public String generateHtmlVisualization(String hash, Model model) {
        model.addAttribute("data", mapService.deserializeMap(hash));
        model.addAttribute("googleMapsScript", googleMapsClient.getScript());
        return "map/index";
    }

    @Override
    public ResponseEntity<InputStreamResource> generateImageVisualization(String hash, Model model) {
        InputStreamResource image = googleMapsClient.getImage(mapService.deserializeMap(hash));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "map_image" + "_" + System.currentTimeMillis() + ".png");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(image);
    }
}
