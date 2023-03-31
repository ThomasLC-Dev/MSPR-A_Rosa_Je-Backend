package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.*;
import fr.cttt.arosaje.repository.PlantImageRepository;
import fr.cttt.arosaje.repository.UserRepository;
import fr.cttt.arosaje.service.PlantService;
import fr.cttt.arosaje.service.RoleService;
import fr.cttt.arosaje.service.UserService;
import fr.cttt.arosaje.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/attachment-files")
public class AttachmentFileController {
    private final StorageService storageService;
    private final UserService userService;
    private final PlantService plantService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PlantImageRepository plantImageRepository;

    public AttachmentFileController(StorageService storageService, UserService userService, PlantService plantService, RoleService roleService, UserRepository userRepository, PlantImageRepository plantImageRepository) {
        this.storageService = storageService;
        this.userService = userService;
        this.plantService = plantService;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.plantImageRepository = plantImageRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadAttachmentFile(@RequestParam(name = "id") Long id, @RequestParam(name = "type") String type, @RequestParam(name = "role", required = false) Optional<String> roleName, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        User user = null;
        Plant plant = null;
        Role role = null;
        if(type.equalsIgnoreCase("user")){
            user = userService.getUser(id);
        }
        else if(type.equalsIgnoreCase("plant") && roleName.isPresent()){
            plant = plantService.getPlant(id);
            role = roleService.getRoleByName(roleName.get());
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        String uploadFileUrl = storageService.uploadFile(multipartFile, type, id);

        if(user != null){
            user.setImageUrl(uploadFileUrl);
            userRepository.save(user);
        }
        else{
            PlantImage plantImage = new PlantImage(null, uploadFileUrl, role, plant);
            plantImageRepository.save(plantImage);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}