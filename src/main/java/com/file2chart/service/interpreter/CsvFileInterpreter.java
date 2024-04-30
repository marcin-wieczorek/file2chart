package com.file2chart.service.interpreter;

import com.file2chart.model.dto.local.ChartModel;
import com.file2chart.model.dto.local.MapModel;
import com.file2chart.model.dto.local.TableModel;
import com.file2chart.model.enums.ChartType;
import com.file2chart.model.enums.FileFormat;
import com.file2chart.service.resolver.CSVFileResolver;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CsvFileInterpreter implements FileInterpreter {

    private final CSVFileResolver csvChartResolvers;

    @Override
    @SneakyThrows
    public ChartModel toChart(MultipartFile file, ChartType chartType) {
        ChartModel chartModel = csvChartResolvers.resolveChartModel(file, chartType);
        return chartModel;
    }

    @Override
    @SneakyThrows
    public MapModel toMap(MultipartFile file) {
        return csvChartResolvers.resolveMapModel(file);
    }

    @Override
    @SneakyThrows
    public TableModel toTable(MultipartFile file) {
        return csvChartResolvers.resolveTableModel(file);
    }

    @Override
    public List<FileFormat> getSupportedFileFormat() {
        return List.of(FileFormat.CSV);
    }

}
