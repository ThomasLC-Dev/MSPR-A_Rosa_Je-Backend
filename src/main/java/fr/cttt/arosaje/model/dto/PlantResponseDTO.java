package fr.cttt.arosaje.model.dto;

import fr.cttt.arosaje.model.PlantImage;
import fr.cttt.arosaje.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String advises;
    private Integer sunLight;
    private Double lowerTemp;
    private Double higherTemp;
    private Double wateringQuantity;
    private Integer wateringFrequency;
    private String wateringContainer;
    private User user;
    private List<PlantImage> imagesUrl;
    private List<PlantImage> keeperImagesUrl;
}
