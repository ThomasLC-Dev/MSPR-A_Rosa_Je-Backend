package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.Chat;
import fr.cttt.arosaje.model.Message;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesByChat(Long chatId);
    Message getMessage(Long id);
    void saveMessage(MessageDTO messageDTO, Chat chat, User user);
    void deleteMessage(Long id);
}
