package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.Slot;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.SlotDTO;

import java.util.List;

public interface SlotService {
    List<Slot> getSlots();
    List<Slot> getSlotsByUser(Long userId);
    List<Slot> getSlotsByGuardian(Long guardianId);
    Slot getSlot(Long id);
    void saveSlot(SlotDTO slotDTO, User user, User guardian);
    void updateSlot(Long id, SlotDTO slotDTO, User user, User guardian);
    void deleteSlot(Long id);
}
