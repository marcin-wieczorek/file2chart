package com.file2chart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    private UUID id;
    private String name;

    private FileDetails fileDetails;
    private Map<String, Object> columnMap;
    private List<Object> dataArray;

    private boolean hitRowLimit;

    private long numColumns;
    private long numRows;
    private long originalNumRows;

    private Instant createdAt;
    private Instant updatedAt;

}
