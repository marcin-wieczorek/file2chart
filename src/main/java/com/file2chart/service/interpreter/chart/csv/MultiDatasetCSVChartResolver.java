package com.file2chart.service.interpreter.chart.csv;

import com.file2chart.model.dto.local.ChartModel;
import com.file2chart.model.enums.ChartType;
import com.file2chart.service.validators.FileValidator;
import com.univocity.parsers.common.record.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MultiDatasetCSVChartResolver extends CSVChartResolver {

    @Override
    public ChartModel resolveModel(MultipartFile file) {
        List<Record> object = getObject(file);

        List<String> headers = getHeaders(object);
        List<List<String>> records = getRecords(object);

        Map<String, List<String>> datasets = transformToMap(headers, records);

        return new ChartModel(datasets);
    }

    @Override
    public boolean isSupported(ChartType chartType) {
        return List.of(ChartType.BAR, ChartType.LINE)
                   .contains(chartType);
    }

    public static Map<String, List<String>> transformToMap(List<String> headers, List<List<String>> records) {
        LinkedHashMap<String, List<String>> datasets = headers.stream()
                                                              .collect(Collectors.toMap(
                                                                      header -> header,
                                                                      header -> new ArrayList<>(),
                                                                      (a, b) -> b,
                                                                      LinkedHashMap::new));

        for (List<String> record : records) {
            for (int i = 0; i < record.size(); i++) {
                String headerElement = headers.get(i);
                String recordValueElement = record.get(i);
                FileValidator.validateNumericValue(recordValueElement);
                datasets.get(headerElement)
                        .add(recordValueElement == null ? "0" : recordValueElement);
            }
        }

        return datasets;
    }
}
