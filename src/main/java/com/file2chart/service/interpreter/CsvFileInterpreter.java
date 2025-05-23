package com.file2chart.service.interpreter;

import com.file2chart.model.dto.local.ChartModel;
import com.file2chart.model.dto.local.MapModel;
import com.file2chart.model.dto.local.TableModel;
import com.file2chart.model.enums.FileFormat;
import com.file2chart.model.enums.PricingPlan;
import com.file2chart.service.resolver.CSVFileResolver;
import lombok.AllArgsConstructor;
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
    public ChartModel toChart(MultipartFile file, PricingPlan pricingPlan) {
        ChartModel chartModel = csvChartResolvers.resolveChartModel(file, pricingPlan);
        return chartModel;
    }

    @Override
    public MapModel toMap(MultipartFile file, PricingPlan pricingPlan) {
        return csvChartResolvers.resolveMapModel(file, pricingPlan);
    }

    @Override
    public TableModel toTable(MultipartFile file, PricingPlan pricingPlan) {
        return csvChartResolvers.resolveTableModel(file, pricingPlan);
    }

    @Override
    public List<FileFormat> getSupportedFileFormat() {
        return List.of(FileFormat.CSV);
    }

}
