package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.Address;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<Address> getAddresses();
    Address getAddress(Long id);
    void saveAddress(AddressDTO addressDTO, User user);
    void updateAddress(Long id, AddressDTO addressDTO, User user);
    void deleteAddress(Long id);
    float[] getGeocodeFromAddress(String address, String postalCode, String city);
}
