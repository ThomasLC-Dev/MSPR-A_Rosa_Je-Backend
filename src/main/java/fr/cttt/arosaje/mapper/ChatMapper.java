package fr.cttt.arosaje.mapper;

import fr.cttt.arosaje.model.Chat;
import fr.cttt.arosaje.model.Message;
import fr.cttt.arosaje.model.dto.ChatResponseDTO;
import fr.cttt.arosaje.model.dto.MessageDTO;
import fr.cttt.arosaje.model.dto.MessageResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class ChatMapper {
    public static ChatResponseDTO chatToDto(Chat chat){
        ChatResponseDTO chatResponseDTO = new ChatResponseDTO();
        chatResponseDTO.setId(chat.getId());
        chatResponseDTO.setLastUpdate(chat.getLastUpdate());
        chatResponseDTO.setUserId(chat.getUser().getId());
        chatResponseDTO.setUserName(chat.getUser().getLastName() + " " + chat.getUser().getFirstName());
        chatResponseDTO.setKeeperId(chat.getKeeper().getId());
        chatResponseDTO.setKeeperName(chat.getKeeper().getLastName() + " " + chat.getKeeper().getFirstName());
        List<MessageResponseDTO> messageResponseDTOs = new ArrayList<>();
        for(Message message : chat.getMessages()){
            MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
            messageResponseDTO.setId(message.getId());
            messageResponseDTO.setContent(message.getContent());
            messageResponseDTO.setUserId(message.getUser().getId());
            messageResponseDTO.setUserName(message.getUser().getLastName() + " " + message.getUser().getFirstName());
            messageResponseDTO.setDate(message.getDate());
            messageResponseDTOs.add(messageResponseDTO);
        }
        chatResponseDTO.setMessageResponseDTOs(messageResponseDTOs);
        return chatResponseDTO;
    }
}
