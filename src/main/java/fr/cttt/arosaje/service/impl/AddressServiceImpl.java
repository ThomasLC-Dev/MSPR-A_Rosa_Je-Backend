package fr.cttt.arosaje.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.Address;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.AddressDTO;
import fr.cttt.arosaje.model.dto.GeocodeDTO;
import fr.cttt.arosaje.repository.AddressRepository;
import fr.cttt.arosaje.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
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
        float[] coordinates = getGeocodeFromAddress(address.getAddress(), address.getPostalCode(), address.getCity());
        address.setLatitude(coordinates[1]);
        address.setLongitude(coordinates[0]);
        address.setUser(user);
        addressRepository.save(address);
    }

    @Override
    public void updateAddress(Long id, AddressDTO addressDTO, User user) {
        Address address = this.getAddress(id);
        address.setAddress((addressDTO.getAddress() == null) ? address.getAddress() : addressDTO.getAddress());
        address.setPostalCode((addressDTO.getPostalCode() == null) ? address.getPostalCode() : addressDTO.getPostalCode());
        address.setCity((addressDTO.getCity() == null) ? address.getCity() : addressDTO.getCity());
        if(addressDTO.getCity() != null){
            float[] coordinates = getGeocodeFromAddress(address.getAddress(), address.getPostalCode(), address.getCity());
            address.setLatitude(coordinates[1]);
            address.setLongitude(coordinates[0]);
        }
        address.setUser((user == null) ? address.getUser() : user);
        addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public float[] getGeocodeFromAddress(String address, String postalCode, String city) {
        final String uri = "https://api-adresse.data.gouv.fr/search/?limit=1&q="+address+"+"+postalCode+"+"+city;
        RestTemplate restTemplate = new RestTemplate();
        GeocodeDTO data = restTemplate.getForObject(uri, GeocodeDTO.class);
        GeocodeDTO.FeatureGeocodeDTO featureGeocodeDTO = data.getFeatures()[0];
        GeocodeDTO.GeometryGeocodeDTO geometryGeocodeDTO = featureGeocodeDTO.getGeometry();
        float[] coordinates = geometryGeocodeDTO.getCoordinates();
        return coordinates;
    }


}
