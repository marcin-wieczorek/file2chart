package com.file2chart.model.dto.local;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocation {
    @NotNull
    private Coordinate coordinate;

    private String description;
}
