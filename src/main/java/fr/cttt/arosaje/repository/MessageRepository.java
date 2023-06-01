package fr.cttt.arosaje.repository;

import fr.cttt.arosaje.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<List<Message>> findAllByChatIdOrderByDateDesc(Long chatId);
}
