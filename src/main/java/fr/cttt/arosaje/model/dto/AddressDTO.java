package fr.cttt.arosaje.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Integer roadNumber;
    private String roadType;
    private String road;
    private String additionalAddress;
    private String postalCode;
    private String city;
    private Long userId;
}
