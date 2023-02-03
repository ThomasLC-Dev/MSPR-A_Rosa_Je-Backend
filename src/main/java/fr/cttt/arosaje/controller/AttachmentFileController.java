package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.AttachmentFile;
import fr.cttt.arosaje.model.Plant;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.AttachmentFileDTO;
import fr.cttt.arosaje.service.AttachmentFileService;
import fr.cttt.arosaje.service.PlantService;
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

@RestController
@RequestMapping("/api/attachment-files")
public class AttachmentFileController {
    private final AttachmentFileService attachmentFileService;
    private final UserService userService;
    private final PlantService plantService;

    public AttachmentFileController(AttachmentFileService attachmentFileService) {
        this.attachmentFileService = attachmentFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadAttachmentFile(@RequestParam(name = "id") Long id, @RequestParam(name = "type") String type, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if(type.equalsIgnoreCase("user")){
            User user = userService.getUser(id);
        }
        else if(type.equalsIgnoreCase("plant")){
            Plant plant = plantService.getPlant(id);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        AttachmentFile attachmentFile = attachmentFileService.uploadAttachmentFile(multipartFile);
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/attachment-files/").path(attachmentFile.getId().toString()).toUriString();
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
