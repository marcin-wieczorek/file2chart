package com.file2chart.model.dto.local;

import com.file2chart.model.enums.SortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sort {
    private String parameter;
    private SortType sortType;
}
