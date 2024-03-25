package com.file2chart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.file2chart.model.dto.local.GenericModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class GenericModelService {
    private final ObjectMapper mapper;

   public GenericModel creteModel(MultipartFile file) {
       /*
       * 1. Pick right converter (that contains separator ruleset)
       * 2. Check the user privileges and crop rows (limits)
       * 3. 
       * */
       return null;
   }
}
