package fr.cttt.arosaje.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantDTO {
    private String name;
    private String description;
    private String advises;
    private Integer sunLight;
    private Double lowerTemp;
    private Double higherTemp;
    private Double wateringQuantity;
    private Integer wateringFrequency;
    private String wateringContainer;
    private Long userId;
}
