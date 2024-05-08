package com.file2chart.model.dto.input;

import com.file2chart.model.enums.ChartType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageChartVisualizationRequest {
    @NotNull
    private String hash;

    @NotNull
    private ChartType chartType;
}
