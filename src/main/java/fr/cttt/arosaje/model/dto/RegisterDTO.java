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
    private String phone;
    private String email;
    private String password;
    private String passwordConfirmation;
    private String status;
    private Integer roadNumber;
    private String roadType;
    private String road;
    private String additionalAddress;
    private String postalCode;
    private String city;
}
