package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.dto.UserDTO;
import fr.cttt.arosaje.repository.UserRepository;
import fr.cttt.arosaje.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("User not found !"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ElementNotFoundException("User not found !"));
    }

    @Override
    public boolean checkUserExist(String email){
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null;
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        User user = new User();
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setStatus(userDTO.getStatus().equals("true"));
        return userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, UserDTO userDTO) {
        User user = this.getUser(id);
        user.setLastName((userDTO.getLastName() == null) ? user.getLastName() : userDTO.getLastName());
        user.setFirstName((userDTO.getFirstName() == null) ? user.getFirstName() : userDTO.getFirstName());
        user.setEmail((userDTO.getEmail() == null) ? user.getEmail() : userDTO.getEmail());
        user.setStatus((userDTO.getStatus() == null) ? user.getStatus() : (userDTO.getStatus().equals("true")));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
