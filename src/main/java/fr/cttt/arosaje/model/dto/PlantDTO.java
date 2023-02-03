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
    private String imageUrl;
    private String advises;
    private Long userId;
}
