package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.Slot;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.SlotDTO;
import fr.cttt.arosaje.service.SlotService;
import fr.cttt.arosaje.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/slots")
public class SlotController {
    private final SlotService slotService;
    private final UserService userService;

    public SlotController(SlotService slotService, UserService userService) {
        this.slotService = slotService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Slot>> getSlots(@RequestParam(name = "user", required = false) Optional<Long> userId, @RequestParam(name = "keeper", required = false) Optional<Long> keeperId){
        if(userId.isPresent()){
            return new ResponseEntity<>(slotService.getSlotsByUser(userId.get()), HttpStatus.OK);
        }
        else if(keeperId.isPresent()){
            return new ResponseEntity<>(slotService.getSlotsByKeeper(keeperId.get()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(slotService.getSlots(), HttpStatus.OK);
        }
    }

    @GetMapping("/{slotId}")
    public ResponseEntity<Slot> getSlot(@PathVariable(name = "slotId") Long id){
        return new ResponseEntity<>(slotService.getSlot(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createSlot(@RequestBody SlotDTO slotDTO){
        User user = userService.getUser(slotDTO.getUserId());
        User guardian = (slotDTO.getKeeperId() == null) ? null : userService.getUser(slotDTO.getKeeperId());
        slotService.saveSlot(slotDTO, user, guardian);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{slotId}")
    public ResponseEntity<?> updateSlot(@PathVariable(name = "slotId") Long id, @RequestBody SlotDTO slotDTO){
        User user = (slotDTO.getUserId() == null) ? null : userService.getUser(slotDTO.getUserId());
        User guardian = (slotDTO.getKeeperId() == null) ? null : userService.getUser(slotDTO.getKeeperId());
        slotService.updateSlot(id, slotDTO, user, guardian);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<?> deleteSlot(@PathVariable(name = "slotId") Long id){
        slotService.deleteSlot(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
