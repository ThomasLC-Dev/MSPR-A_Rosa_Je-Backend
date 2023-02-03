package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.AttachmentFile;
import fr.cttt.arosaje.repository.AttachmentFileRepository;
import fr.cttt.arosaje.service.AttachmentFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AttachmentFileServiceImpl implements AttachmentFileService {
    private final AttachmentFileRepository attachmentFileRepository;

    public AttachmentFileServiceImpl(AttachmentFileRepository attachmentFileRepository) {
        this.attachmentFileRepository = attachmentFileRepository;
    }

    @Override
    public AttachmentFile getAttachmentFile(Long id) {
        return attachmentFileRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("File not found !"));
    }

    @Override
    public AttachmentFile uploadAttachmentFile(MultipartFile file) throws IOException {
        String fileName = "F"+ UUID.randomUUID();
        AttachmentFile attachmentFile = new AttachmentFile(null, fileName, file.getContentType(), file.getBytes());
        return attachmentFileRepository.save(attachmentFile);
    }
}
