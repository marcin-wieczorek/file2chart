package com.file2chart.config.converter;

import com.file2chart.model.enums.ChartType;
import org.springframework.core.convert.converter.Converter;

public class StringToChartTypeConverter implements Converter<String, ChartType> {
    @Override
    public ChartType convert(String source) {
        return ChartType.valueOf(source.toUpperCase());
    }
}
