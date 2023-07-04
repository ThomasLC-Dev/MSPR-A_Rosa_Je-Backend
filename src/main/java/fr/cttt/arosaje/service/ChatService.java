package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.Chat;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.ChatDTO;

import java.util.List;

public interface ChatService {
    List<Chat> getChatsByUser(Long userId);
    Chat getChat(Long id);
    Chat saveChat(ChatDTO chatDTO, User user, User keeper);
    void updateChat(Long id);
    void deleteChat(Long id);
    boolean chatAlreadyExist(Long userId, Long keeperId);
}
