package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.mapper.ChatMapper;
import fr.cttt.arosaje.model.Chat;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.ChatDTO;
import fr.cttt.arosaje.model.dto.ChatResponseDTO;
import fr.cttt.arosaje.service.ChatService;
import fr.cttt.arosaje.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Chat>> getChats(@RequestParam(name = "user", required = false) Optional<Long> userId){
        List<Chat> chats = new ArrayList<>();
        if(userId.isPresent()){
            chats = chatService.getChatsByUser(userId.get());
        }
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatResponseDTO> getChat(@PathVariable(name = "chatId") Long id){
        return new ResponseEntity<>(ChatMapper.chatToDto(chatService.getChat(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody ChatDTO chatDTO){
        User user = userService.getUser(chatDTO.getUserId());
        User keeper = userService.getUser(chatDTO.getKeeperId());
        return new ResponseEntity<>(chatService.saveChat(chatDTO, user, keeper), HttpStatus.CREATED);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<?> deleteChat(@PathVariable(name = "chatId") Long id){
        chatService.deleteChat(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
