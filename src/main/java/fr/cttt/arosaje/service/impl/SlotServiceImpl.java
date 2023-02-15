package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.Slot;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.SlotDTO;
import fr.cttt.arosaje.repository.SlotRepository;
import fr.cttt.arosaje.service.SlotService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotServiceImpl implements SlotService {
    private final SlotRepository slotRepository;

    public SlotServiceImpl(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public List<Slot> getSlots() {
        return slotRepository.findAll();
    }

    @Override
    public List<Slot> getSlotsByUser(Long userId) {
        return slotRepository.findAllByUserId(userId).orElseThrow(() -> new ElementNotFoundException("Any slots found !"));
    }

    @Override
    public List<Slot> getSlotsByKeeper(Long keeperId) {
        return slotRepository.findAllByKeeperId(keeperId).orElseThrow(() -> new ElementNotFoundException("Any slots found !"));
    }

    @Override
    public Slot getSlot(Long id) {
        return slotRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Slot not found !"));
    }

    @Override
    public void saveSlot(SlotDTO slotDTO, User user, User keeper) {
        Slot slot = new Slot();
        slot.setStartDate(slotDTO.getStartDate());
        slot.setEndDate(slotDTO.getEndDate());
        slot.setUser(user);
        slot.setKeeper(keeper);
        slotRepository.save(slot);
    }

    @Override
    public void updateSlot(Long id, SlotDTO slotDTO, User user, User keeper) {
        Slot slot = this.getSlot(id);
        slot.setStartDate((slotDTO.getStartDate() == null) ? slot.getStartDate() : slotDTO.getStartDate());
        slot.setEndDate((slotDTO.getEndDate() == null) ? slot.getEndDate() : slotDTO.getEndDate());
        slot.setUser((user == null) ? slot.getUser() : user);
        slot.setKeeper((keeper == null) ? slot.getKeeper() : keeper);
        slotRepository.save(slot);
    }

    @Override
    public void deleteSlot(Long id) {
        slotRepository.deleteById(id);
    }
}
