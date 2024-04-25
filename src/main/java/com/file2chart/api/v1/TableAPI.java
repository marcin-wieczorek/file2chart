package com.file2chart.api.v1;

import com.file2chart.model.dto.output.VisualizationData;
import com.file2chart.model.enums.VisualizationType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface TableAPI {
    @PostMapping("/table/hash")
    ResponseEntity<VisualizationData> generateTableHash(@RequestParam MultipartFile file,
                                                        @RequestParam VisualizationType type);

    @GetMapping("/table/visualization/html")
    String generateHtmlVisualization(@RequestParam String hash, Model model);

    @GetMapping("/table/visualization/image")
    ResponseEntity<InputStreamResource> generateImageVisualization(@RequestParam String hash, Model model);
}
