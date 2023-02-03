package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.AttachmentFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachmentFileService {
    AttachmentFile getAttachmentFile(Long id);
    AttachmentFile uploadAttachmentFile(MultipartFile file) throws IOException;
}
