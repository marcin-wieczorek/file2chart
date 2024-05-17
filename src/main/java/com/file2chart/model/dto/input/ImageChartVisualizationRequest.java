package com.file2chart.model.dto.input;

import com.file2chart.model.enums.ChartType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
