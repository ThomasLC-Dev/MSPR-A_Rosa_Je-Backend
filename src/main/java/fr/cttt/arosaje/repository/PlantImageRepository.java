package fr.cttt.arosaje.repository;

import fr.cttt.arosaje.model.PlantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantImageRepository extends JpaRepository<PlantImage, Long> {
    Optional<List<PlantImage>> findAllByPlantId(Long plantId);
    Optional<List<PlantImage>> findAllByPlantIdAndRoleId(Long plantId, Long roleId);
    void deleteAllByPlantId(Long plantId);
}
