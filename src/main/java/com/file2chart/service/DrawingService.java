package com.file2chart.service;

import com.file2chart.model.dto.output.ChartOutput;
import com.file2chart.model.dto.output.TableOutput;
import com.file2chart.service.compression.DataCompressionService;
import com.file2chart.service.utils.CryptoService;
import com.file2chart.service.utils.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class DrawingService {

    private final JsonConverter jsonConverter;
    private final CryptoService cryptoService;
    private final DataCompressionService dataCompressionService;

    public TableOutput drawTable(String data) {
        return drawObject(data, TableOutput.class);
    }

    public ChartOutput drawChart(String data) {
        return drawObject(data, ChartOutput.class);
    }

    private <T> T drawObject(String data, Class<T> clazz) {
        var decryptedMessage = cryptoService.decrypt(data);
        var decompressedJson = dataCompressionService.decompress(decryptedMessage);
        T object = jsonConverter.toObject(decompressedJson, clazz);

        return object;
    }
}
