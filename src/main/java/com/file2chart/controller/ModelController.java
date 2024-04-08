//package com.file2chart.controller;
//
//import com.file2chart.model.dto.local.GenericModel;
//import com.file2chart.service.GenericModelService;
//import com.file2chart.service.files.FileValidator;
//import lombok.AllArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/v2")
//public class ModelController {
//
//    private final GenericModelService genericModelService;
//
//    @PostMapping(
//            value = "/model",
//            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE}
//    )
//    public ResponseEntity<GenericModel> upload(@RequestParam("file") MultipartFile file) {
//        FileValidator.validateFileFormat(file);
//        GenericModel model = genericModelService.creteModel(file);
//        return ResponseEntity.ok(model);
//    }
//}
