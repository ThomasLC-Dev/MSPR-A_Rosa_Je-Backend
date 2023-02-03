package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.Address;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.AddressDTO;
import fr.cttt.arosaje.service.AddressService;
import fr.cttt.arosaje.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAddresses(){
        return new ResponseEntity<>(addressService.getAddresses(), HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddress(@PathVariable(name = "addressId") Long id){
        return new ResponseEntity<>(addressService.getAddress(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody AddressDTO addressDTO){
        User user = userService.getUser(addressDTO.getUserId());
        addressService.saveAddress(addressDTO, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable(name = "addressId") Long id, @RequestBody AddressDTO addressDTO){
        User user = (addressDTO.getUserId() == null) ? null : userService.getUser(addressDTO.getUserId());
        addressService.updateAddress(id, addressDTO, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable(name = "addressId") Long id){
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
