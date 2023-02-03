package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.Plant;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.PlantDTO;
import fr.cttt.arosaje.service.PlantService;
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
    private final UserService userService;

    public PlantController(PlantService plantService, UserService userService) {
        this.plantService = plantService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Plant>> getPlants(@RequestParam(name = "user", required = false) Optional<Long> userId){
        if(userId.isPresent()){
            return new ResponseEntity<>(plantService.getPlantsByUser(userId.get()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(plantService.getPlants(), HttpStatus.OK);
        }
    }

    @GetMapping("/{plantId}")
    public ResponseEntity<Plant> getPlant(@PathVariable(name = "plantId") Long id){
        return new ResponseEntity<>(plantService.getPlant(id), HttpStatus.OK);
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
