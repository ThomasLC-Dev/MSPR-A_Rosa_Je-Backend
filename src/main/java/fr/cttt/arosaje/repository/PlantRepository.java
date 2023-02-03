package fr.cttt.arosaje.repository;

import fr.cttt.arosaje.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    Optional<List<Plant>> findAllByUserId(Long userId);
}
