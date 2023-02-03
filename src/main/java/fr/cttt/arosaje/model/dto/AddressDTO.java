package fr.cttt.arosaje.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String address;
    private String postalCode;
    private String city;
    private Long userId;
}
