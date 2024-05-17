package com.file2chart.model.dto.local;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
    @NotNull
    private String latitude;

    @NotNull
    private String longitude;
}
