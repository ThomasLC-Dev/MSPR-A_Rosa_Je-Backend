package fr.cttt.arosaje.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;
    private Long keeperId;
}
