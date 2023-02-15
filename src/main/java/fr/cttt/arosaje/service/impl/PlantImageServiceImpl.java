package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.PlantImage;
import fr.cttt.arosaje.repository.PlantImageRepository;
import fr.cttt.arosaje.service.PlantImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantImageServiceImpl implements PlantImageService {
    private final PlantImageRepository plantImageRepository;

    public PlantImageServiceImpl(PlantImageRepository plantImageRepository) {
        this.plantImageRepository = plantImageRepository;
    }

    @Override
    public List<PlantImage> getPlantImagesByPlant(Long plantId) {
        return plantImageRepository.findAllByPlantId(plantId).orElseThrow(() -> new ElementNotFoundException("Any images found !"));
    }

    @Override
    public List<PlantImage> getPlantImagesByPlantAndRole(Long plantId, Long roleId) {
        return plantImageRepository.findAllByPlantIdAndRoleId(plantId, roleId).orElseThrow(() -> new ElementNotFoundException("Any images found !"));
    }

    @Override
    public void deletePlantImagesByPlant(Long plantId) {
        plantImageRepository.deleteAllByPlantId(plantId);
    }
}
