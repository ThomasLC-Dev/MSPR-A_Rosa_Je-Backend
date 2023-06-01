package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.Chat;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.ChatDTO;
import fr.cttt.arosaje.model.dto.MessageDTO;
import fr.cttt.arosaje.service.ChatService;
import fr.cttt.arosaje.service.MessageService;
import fr.cttt.arosaje.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;
    private final ChatService chatService;
    private final UserService userService;

    public MessageController(MessageService messageService, ChatService chatService, UserService userService) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody MessageDTO messageDTO){
        User user = userService.getUser(messageDTO.getUserId());
        Chat chat = chatService.getChat(messageDTO.getChatId());
        messageService.saveMessage(messageDTO, chat, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable(name = "messageId") Long id){
        messageService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
