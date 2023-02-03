package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.Plant;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.PlantDTO;
import fr.cttt.arosaje.repository.PlantRepository;
import fr.cttt.arosaje.service.PlantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {
    private final PlantRepository plantRepository;

    public PlantServiceImpl(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @Override
    public List<Plant> getPlants() {
        return plantRepository.findAll();
    }

    @Override
    public List<Plant> getPlantsByUser(Long userId) {
        return plantRepository.findAllByUserId(userId).orElseThrow(() -> new ElementNotFoundException("Any plants found !"));
    }

    @Override
    public Plant getPlant(Long id) {
        return plantRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Plant not found !"));
    }

    @Override
    public void savePlant(PlantDTO plantDTO, User user) {
        Plant plant = new Plant();
        plant.setName(plantDTO.getName());
        plant.setDescription(plantDTO.getDescription());
        plant.setImageUrl(plantDTO.getImageUrl());
        plant.setAdvises(plantDTO.getAdvises());
        plant.setUser(user);
        plantRepository.save(plant);
    }

    @Override
    public void updatePlant(Long id, PlantDTO plantDTO, User user) {
        Plant plant = this.getPlant(id);
        plant.setName((plantDTO.getName() == null) ? plant.getName() : plantDTO.getName());
        plant.setDescription((plantDTO.getDescription() == null) ? plant.getDescription() : plantDTO.getDescription());
        plant.setImageUrl((plantDTO.getImageUrl() == null) ? plant.getImageUrl() : plantDTO.getImageUrl());
        plant.setAdvises((plantDTO.getAdvises() == null) ? plant.getAdvises() : plantDTO.getAdvises());
        plant.setUser((user == null) ? plant.getUser() : user);
        plantRepository.save(plant);
    }

    @Override
    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }
}
