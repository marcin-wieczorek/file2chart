package com.file2chart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.file2chart.model.Model;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class ModelService {
    private final ObjectMapper mapper;

   public Model creteModel(MultipartFile file) {
       /*
       * 1. Pick right converter (that contains separator ruleset)
       * 2. Check the user privileges and crop rows (limits)
       * 3. 
       * */
       return null;
   }
}
