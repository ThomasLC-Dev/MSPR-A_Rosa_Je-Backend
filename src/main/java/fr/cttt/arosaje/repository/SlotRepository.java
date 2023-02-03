package fr.cttt.arosaje.repository;

import fr.cttt.arosaje.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    Optional<List<Slot>> findAllByUserId(Long userId);
    Optional<List<Slot>> findAllByGuardianId(Long guardianId);
}
