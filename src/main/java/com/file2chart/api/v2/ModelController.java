package com.file2chart.api.v2;

import com.file2chart.model.Model;
import com.file2chart.service.ModelService;
import com.file2chart.service.FileFormatValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/v2")
public class ModelController {

    private final ModelService modelService;

    @PostMapping(
            value = "/model",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Model> upload(@RequestParam("file") MultipartFile file) {
        FileFormatValidator.validate(file);
        Model model = modelService.creteModel(file);
        return ResponseEntity.ok(model);
    }
}
