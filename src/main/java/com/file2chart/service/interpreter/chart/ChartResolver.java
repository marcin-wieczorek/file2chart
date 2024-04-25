package com.file2chart.service.interpreter.chart;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChartResolver<T> {
    T getObject(MultipartFile file);

    List<String> getHeaders(T object);

    List<List<String>> getRecords(T object);

}
