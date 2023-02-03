package fr.cttt.arosaje.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private String passwordConfirmation;
    private String status;
    private String address;
    private String postalCode;
    private String city;
}
