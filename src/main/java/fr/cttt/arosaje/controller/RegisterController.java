package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.ForgotPasswordToken;
import fr.cttt.arosaje.model.Role;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.AddressDTO;
import fr.cttt.arosaje.model.dto.RegisterDTO;
import fr.cttt.arosaje.model.dto.UpdatePasswordDTO;
import fr.cttt.arosaje.model.dto.UserDTO;
import fr.cttt.arosaje.repository.UserRepository;
import fr.cttt.arosaje.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class RegisterController {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final ForgotPasswordTokenService forgotPasswordTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterController(UserService userService, UserRoleService userRoleService, RoleService roleService, UserRepository userRepository, AddressService addressService, ForgotPasswordTokenService forgotPasswordTokenService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.forgotPasswordTokenService = forgotPasswordTokenService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        if(!userService.checkUserExist(registerDTO.getEmail())){
            if(registerDTO.getPassword().equals(registerDTO.getPasswordConfirmation())){
                UserDTO userDTO = new UserDTO(registerDTO.getLastName(), registerDTO.getFirstName(), registerDTO.getPhone(), registerDTO.getEmail(), registerDTO.getPassword(), registerDTO.getStatus());
                User user = userService.saveUser(userDTO);
                AddressDTO addressDTO = new AddressDTO(registerDTO.getRoadNumber(), registerDTO.getRoadType(), registerDTO.getRoad(), registerDTO.getAdditionalAddress(), registerDTO.getPostalCode(), registerDTO.getCity(), user.getId());
                addressService.saveAddress(addressDTO, user);
                for(Long roleId : registerDTO.getUserRoles()){
                    userRoleService.saveUserRole(user, roleService.getRole(roleId));
                }
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/forgot-password/request")
    public ResponseEntity<?> forgotPasswordRequest(@RequestParam(name = "email") String email){
        if(userService.checkUserExist(email)){
            User user = userService.getUserByEmail(email);
            forgotPasswordTokenService.deleteForgotPasswordTokenByUser(user);
            ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenService.saveForgotPasswordToken(user);
            System.out.println(forgotPasswordToken.getToken());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/forgot-password/update")
    public ResponseEntity<?> forgotPasswordUpdate(@RequestParam(name = "token") String forgotPasswordTokenParam, @RequestBody UpdatePasswordDTO updatePasswordDTO){
        ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenService.getForgotPasswordTokenByToken(forgotPasswordTokenParam);
        User user = forgotPasswordToken.getUser();
        if(updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getNewPasswordConfirmation())){
            user.setPassword(bCryptPasswordEncoder.encode(updatePasswordDTO.getNewPassword()));
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO){
        User user = userService.getUser(updatePasswordDTO.getUserId());
        if(bCryptPasswordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())){
            if(updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getNewPasswordConfirmation())){
                user.setPassword(bCryptPasswordEncoder.encode(updatePasswordDTO.getNewPassword()));
                userRepository.save(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
