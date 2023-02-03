package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.Plant;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.PlantDTO;

import java.util.List;

public interface PlantService {
    List<Plant> getPlants();
    List<Plant> getPlantsByUser(Long userId);
    Plant getPlant(Long id);
    void savePlant(PlantDTO plantDTO, User user);
    void updatePlant(Long id, PlantDTO plantDTO, User user);
    void deletePlant(Long id);
}
