package com.file2chart.config.converter;

import com.file2chart.model.enums.ChartType;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public class StringToChartTypeEnumConverter implements Converter<String, ChartType> {
    @Override
    public ChartType convert(String source) {
        ChartType chartType = ChartType.getByType(source);
        return Optional.ofNullable(chartType)
                       .orElseGet(() -> ChartType.valueOf(source.toUpperCase()));
    }
}
