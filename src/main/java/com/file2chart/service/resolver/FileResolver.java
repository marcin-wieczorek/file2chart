package com.file2chart.service.resolver;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileResolver<T> {
    T getObject(MultipartFile file);

    List<String> getHeaders(T object);

    List<List<String>> getRecords(T object);

    List<String> getDescriptions(T object);

}
