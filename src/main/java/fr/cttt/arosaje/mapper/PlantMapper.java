package fr.cttt.arosaje.mapper;

import fr.cttt.arosaje.model.Plant;
import fr.cttt.arosaje.model.PlantImage;
import fr.cttt.arosaje.model.dto.PlantResponseDTO;

import java.util.List;

public class PlantMapper {
    public static PlantResponseDTO plantToDto(Plant plant, List<PlantImage> plantImages, List<PlantImage> plantKeeperImages){
        PlantResponseDTO plantResponseDTO = new PlantResponseDTO();
        plantResponseDTO.setId(plant.getId());
        plantResponseDTO.setName(plant.getName());
        plantResponseDTO.setDescription(plant.getDescription());
        plantResponseDTO.setAdvises(plant.getAdvises());
        plantResponseDTO.setSunLight(plant.getSunLight());
        plantResponseDTO.setLowerTemp(plant.getLowerTemp());
        plantResponseDTO.setHigherTemp(plant.getHigherTemp());
        plantResponseDTO.setWateringQuantity(plant.getWateringQuantity());
        plantResponseDTO.setWateringFrequency(plant.getWateringFrequency());
        plantResponseDTO.setWateringContainer(plant.getWateringContainer());
        plantResponseDTO.setUser(plant.getUser());
        plantResponseDTO.setImagesUrl(plantImages);
        plantResponseDTO.setKeeperImagesUrl(plantKeeperImages);
        return plantResponseDTO;
    }
}
