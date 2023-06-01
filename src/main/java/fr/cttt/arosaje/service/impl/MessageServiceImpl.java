package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.Chat;
import fr.cttt.arosaje.model.Message;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.MessageDTO;
import fr.cttt.arosaje.repository.MessageRepository;
import fr.cttt.arosaje.service.ChatService;
import fr.cttt.arosaje.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;

    public MessageServiceImpl(MessageRepository messageRepository, ChatService chatService) {
        this.messageRepository = messageRepository;
        this.chatService = chatService;
    }

    @Override
    public List<Message> getMessagesByChat(Long chatId) {
        return messageRepository.findAllByChatIdOrderByDateDesc(chatId).orElse(new ArrayList<>());
    }

    @Override
    public Message getMessage(Long id) {
        return messageRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Message not found !"));
    }

    @Override
    public void saveMessage(MessageDTO messageDTO, Chat chat, User user) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setDate(new Date());
        message.setChat(chat);
        message.setUser(user);
        messageRepository.save(message);

        chatService.updateChat(chat.getId());
    }

    @Override
    public void deleteMessage(Long id) {

    }
}
