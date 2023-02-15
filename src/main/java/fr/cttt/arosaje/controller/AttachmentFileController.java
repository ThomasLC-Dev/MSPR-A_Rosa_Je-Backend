package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.*;
import fr.cttt.arosaje.repository.PlantImageRepository;
import fr.cttt.arosaje.repository.UserRepository;
import fr.cttt.arosaje.service.AttachmentFileService;
import fr.cttt.arosaje.service.PlantService;
import fr.cttt.arosaje.service.RoleService;
import fr.cttt.arosaje.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/attachment-files")
public class AttachmentFileController {
    private final AttachmentFileService attachmentFileService;
    private final UserService userService;
    private final PlantService plantService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PlantImageRepository plantImageRepository;

    public AttachmentFileController(AttachmentFileService attachmentFileService, UserService userService, PlantService plantService, RoleService roleService, UserRepository userRepository, PlantImageRepository plantImageRepository) {
        this.attachmentFileService = attachmentFileService;
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

        AttachmentFile attachmentFile = attachmentFileService.uploadAttachmentFile(multipartFile);
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/attachment-files/").path(attachmentFile.getId().toString()).toUriString();

        if(user != null){
            user.setImageUrl(fileUrl);
            userRepository.save(user);
        }
        else{
            PlantImage plantImage = new PlantImage(null, fileUrl, role, plant);
            plantImageRepository.save(plantImage);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{fileAttachmentId}")
    public ResponseEntity<Resource> downloadAttachmentFile(@PathVariable(name = "fileAttachmentId") Long id){
        AttachmentFile attachmentFile = attachmentFileService.getAttachmentFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachmentFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + attachmentFile.getName() + "\"")
                .body(new ByteArrayResource(attachmentFile.getData()));
    }
}
