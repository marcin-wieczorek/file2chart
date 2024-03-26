package com.file2chart.model.dto.output;

import com.file2chart.model.dto.local.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapOutput {
    private List<GeoLocation> geoLocations;
}
