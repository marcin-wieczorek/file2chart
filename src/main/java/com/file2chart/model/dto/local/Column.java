package com.file2chart.model.dto.local;

import com.file2chart.model.enums.SeparatorType;
import com.file2chart.model.enums.ValueType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Column {
    private UUID id;
    private String name;
    private String example;

    private String largestDist;
    private long cardinality; // % of different values

    private Map<ValueType, Long> valueTypesCount;
    private Map<SeparatorType, Long> separatorCounts; // u or ; or ,

    private ValueType type;
    private SeparatorType separatorType;

    private boolean isPrimaryKey;
}
