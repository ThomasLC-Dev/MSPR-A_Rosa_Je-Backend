package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.PlantImage;

import java.util.List;

public interface PlantImageService {
    List<PlantImage> getPlantImagesByPlant(Long plantId);
    List<PlantImage> getPlantImagesByPlantAndRole(Long plantId, Long roleId);
    void deletePlantImagesByPlant(Long plantId);
}
