package fr.cttt.arosaje.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeocodeDTO {
    private FeatureGeocodeDTO[] features;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class FeatureGeocodeDTO {
        private GeometryGeocodeDTO geometry;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class GeometryGeocodeDTO{
        private float[] coordinates;
    }
}
