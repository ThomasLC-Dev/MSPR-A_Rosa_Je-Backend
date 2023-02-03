package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.Address;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.AddressDTO;
import fr.cttt.arosaje.repository.AddressRepository;
import fr.cttt.arosaje.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddress(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Address not found !"));
    }

    @Override
    public void saveAddress(AddressDTO addressDTO, User user) {
        Address address = new Address();
        address.setAddress(addressDTO.getAddress());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCity(addressDTO.getCity());
        address.setLatitude(0.0f);
        address.setLongitude(0.0f);
        address.setUser(user);
        addressRepository.save(address);
    }

    @Override
    public void updateAddress(Long id, AddressDTO addressDTO, User user) {
        Address address = this.getAddress(id);
        address.setAddress((addressDTO.getAddress() == null) ? address.getAddress() : addressDTO.getAddress());
        address.setPostalCode((addressDTO.getPostalCode() == null) ? address.getPostalCode() : addressDTO.getPostalCode());
        address.setCity((addressDTO.getCity() == null) ? address.getCity() : addressDTO.getCity());
//        address.setLatitude();
//        address.setLongitude();
        address.setUser((user == null) ? address.getUser() : user);
        addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
