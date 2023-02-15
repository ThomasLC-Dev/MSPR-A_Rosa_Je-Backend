package fr.cttt.arosaje.service;


import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUser(Long id);
    User getUserByEmail(String email);
    boolean checkUserExist(String email);
    User saveUser(UserDTO userDTO);
    void updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}
