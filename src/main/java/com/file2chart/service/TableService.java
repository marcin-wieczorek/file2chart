package com.file2chart.service;

import com.file2chart.model.dto.local.CSVModel;
import com.file2chart.model.dto.output.TableOutput;
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
public class TableService {

    private final SecureDataProcessorService secureDataProcessorService;

    @SneakyThrows
    public TableOutput generateHtmlTableData(MultipartFile file) {
        CSVModel csvModel = FileConverter.toCSV(file.getInputStream(), 10l);
        return TableOutput.builder()
                          .headers(csvModel.getHeaders())
                          .cells(csvModel.getCells())
                          .build();
    }

    public String serializeTable(TableOutput table) {
        return secureDataProcessorService.serialize(table);
    }

    public TableOutput deserializeTable(String data) {
        return secureDataProcessorService.deserialize(data, TableOutput.class);
    }

}
