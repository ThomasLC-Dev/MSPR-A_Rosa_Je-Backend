package fr.cttt.arosaje.repository;

import fr.cttt.arosaje.model.AttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, Long> {
}
