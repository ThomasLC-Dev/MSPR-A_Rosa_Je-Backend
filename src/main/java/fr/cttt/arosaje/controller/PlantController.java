package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.mapper.PlantMapper;
import fr.cttt.arosaje.model.Plant;
import fr.cttt.arosaje.model.PlantImage;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.PlantDTO;
import fr.cttt.arosaje.model.dto.PlantResponseDTO;
import fr.cttt.arosaje.service.PlantImageService;
import fr.cttt.arosaje.service.PlantService;
import fr.cttt.arosaje.service.RoleService;
import fr.cttt.arosaje.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plants")
public class PlantController {
    private final PlantService plantService;
    private final PlantImageService plantImageService;
    private final UserService userService;
    private final RoleService roleService;

    public PlantController(PlantService plantService, PlantImageService plantImageService, UserService userService, RoleService roleService) {
        this.plantService = plantService;
        this.plantImageService = plantImageService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<PlantResponseDTO>> getPlants(@RequestParam(name = "user", required = false) Optional<Long> userId){
        List<Plant> plants;
        if(userId.isPresent()){
            plants = plantService.getPlantsByUser(userId.get());
        }
        else{
            plants = plantService.getPlants();
        }

        List<PlantResponseDTO> plantResponseDTOList = plants.stream().map(plant -> {
            List<PlantImage> plantImages = plantImageService.getPlantImagesByPlantAndRole(plant.getId(), roleService.getRoleByName("user").getId());
            List<PlantImage> plantKeeperImages = plantImageService.getPlantImagesByPlantAndRole(plant.getId(), roleService.getRoleByName("keeper").getId());
            return PlantMapper.plantToDto(plant, plantImages, plantKeeperImages);
        }).toList();

        return new ResponseEntity<>(plantResponseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{plantId}")
    public ResponseEntity<PlantResponseDTO> getPlant(@PathVariable(name = "plantId") Long id){
        List<PlantImage> plantImages = plantImageService.getPlantImagesByPlantAndRole(id, roleService.getRoleByName("user").getId());
        List<PlantImage> plantKeeperImages = plantImageService.getPlantImagesByPlantAndRole(id, roleService.getRoleByName("keeper").getId());
        Plant plant = plantService.getPlant(id);
        PlantResponseDTO plantResponseDTO = PlantMapper.plantToDto(plant, plantImages, plantKeeperImages);
        return new ResponseEntity<>(plantResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPlant(@RequestBody PlantDTO plantDTO){
        User user = userService.getUser(plantDTO.getUserId());
        plantService.savePlant(plantDTO, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{plantId}")
    public ResponseEntity<?> updatePlant(@PathVariable(name = "plantId") Long id, @RequestBody PlantDTO plantDTO){
        User user = (plantDTO.getUserId() == null) ? null : userService.getUser(plantDTO.getUserId());
        plantService.updatePlant(id, plantDTO, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{plantId}")
    public ResponseEntity<?> deletePlant(@PathVariable(name = "plantId") Long id){
        plantService.deletePlant(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
