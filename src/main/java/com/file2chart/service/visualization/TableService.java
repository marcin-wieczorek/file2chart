package com.file2chart.service.visualization;

import com.file2chart.model.dto.local.TableModel;
import com.file2chart.model.dto.output.TableOutput;
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
public class TableService {

    private final FileInterpreterLoader interpreterLoader;
    private final SecureDataProcessorService secureDataProcessorService;

    @SneakyThrows
    public TableOutput generateHtmlTableData(MultipartFile file) {
        FileInterpreter fileInterpreter = interpreterLoader.loadInterpreter(file);
        TableModel tableModel = fileInterpreter.toTable(file);
        return TableOutput.builder()
                          .headers(tableModel.getHeaders())
                          .cells(tableModel.getCells())
                          .build();
    }

    public String serializeTable(TableOutput table) {
        return secureDataProcessorService.serialize(table);
    }

    public TableOutput deserializeTable(String data) {
        return secureDataProcessorService.deserialize(data, TableOutput.class);
    }

}
