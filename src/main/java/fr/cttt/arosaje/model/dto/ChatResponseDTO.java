package fr.cttt.arosaje.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDTO {
    private Long id;
    private Date lastUpdate;
    private Long userId;
    private String userName;
    private Long keeperId;
    private String keeperName;
    private List<MessageResponseDTO> messageResponseDTOs;
}
