package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.Chat;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.ChatDTO;
import fr.cttt.arosaje.repository.ChatRepository;
import fr.cttt.arosaje.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public List<Chat> getChatsByUser(Long userId) {
        return chatRepository.findAllByUserIdOrKeeperIdOrderByLastUpdateDesc(userId, userId).orElse(new ArrayList<>());
    }

    @Override
    public Chat getChat(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Chat not found !"));
    }

    @Override
    public Chat saveChat(ChatDTO chatDTO, User user, User keeper) {
        if(chatAlreadyExist(user.getId(), keeper.getId()));
        Chat chat = new Chat();
        chat.setLastUpdate(new Date());
        chat.setUser(user);
        chat.setKeeper(keeper);
        return chatRepository.save(chat);
    }

    @Override
    public void updateChat(Long id){
        Chat chat = this.getChat(id);
        chat.setLastUpdate(new Date());
    }

    @Override
    public void deleteChat(Long id) {
        chatRepository.deleteById(id);
    }

    @Override
    public boolean chatAlreadyExist(Long userId, Long keeperId) {
        Chat chat1 = chatRepository.findChatByUserIdAndKeeperId(userId, keeperId).orElse(null);
        Chat chat2 = chatRepository.findChatByUserIdAndKeeperId(keeperId, userId).orElse(null);
        if(chat1 != null || chat2 != null){
            return true;
        }
        else{
            return false;
        }
    }
}
