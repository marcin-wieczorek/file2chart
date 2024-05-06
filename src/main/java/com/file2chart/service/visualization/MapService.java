package com.file2chart.service.visualization;

import com.file2chart.model.dto.local.MapModel;
import com.file2chart.model.dto.output.MapOutput;
import com.file2chart.service.compression.SecureDataProcessorService;
import com.file2chart.service.interpreter.FileInterpreter;
import com.file2chart.service.interpreter.FileInterpreterLoader;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class MapService {

    private final FileInterpreterLoader interpreterLoader;
    private final SecureDataProcessorService secureDataProcessorService;

    @SneakyThrows
    public MapOutput generateMapData(MultipartFile file) {
        FileInterpreter fileInterpreter = interpreterLoader.loadInterpreter(file);
        MapModel mapModel = fileInterpreter.toMap(file);
        return MapOutput.builder()
                        .geoLocations(mapModel.getGeoLocations())
                        .build();
    }

    public String serializeMap(MapOutput map) {
        return secureDataProcessorService.serialize(map);
    }

    public MapOutput deserializeMap(String data) {
        return secureDataProcessorService.deserialize(data, MapOutput.class);
    }

}
