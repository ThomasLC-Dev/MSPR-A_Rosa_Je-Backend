package fr.cttt.arosaje.repository;

import fr.cttt.arosaje.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<List<Chat>> findAllByUserIdOrKeeperIdOrderByLastUpdateDesc(Long userId, Long keeperId);
    Optional<Chat> findChatByUserIdAndKeeperId(Long userId, Long keeperId);
}
