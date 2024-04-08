package com.file2chart.service;

import com.file2chart.model.dto.local.CSVModel;
import com.file2chart.model.dto.output.MapOutput;
import com.file2chart.service.files.FileConverter;
import com.file2chart.service.utils.SecureDataProcessorService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class MapService {

    private final SecureDataProcessorService secureDataProcessorService;

    @SneakyThrows
    public MapOutput generateHtmlMapData(MultipartFile file) {
        CSVModel csvModel = FileConverter.toCSV(file.getInputStream(), 10l);
        return MapOutput.builder()
                          .build();
    }

    public String serializeMap(MapOutput map) {
        return secureDataProcessorService.serialize(map);
    }

    public MapOutput deserializeMap(String data) {
        return secureDataProcessorService.deserialize(data, MapOutput.class);
    }

}
