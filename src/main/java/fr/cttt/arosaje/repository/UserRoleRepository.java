package fr.cttt.arosaje.repository;

import fr.cttt.arosaje.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<List<UserRole>> findAllByUserId(Long userId);
    void deleteByUserIdAndRoleId(Long userId, Long roleId);
}
