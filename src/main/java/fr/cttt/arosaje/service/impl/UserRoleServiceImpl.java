package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.Role;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.UserRole;
import fr.cttt.arosaje.model.dto.UserRoleDTO;
import fr.cttt.arosaje.repository.UserRoleRepository;
import fr.cttt.arosaje.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<UserRole> getUserRoles() {
        return userRoleRepository.findAll();
    }

    @Override
    public List<UserRole> getUserRolesByUser(Long userId) {
        return userRoleRepository.findAllByUserId(userId).orElseThrow(() -> new ElementNotFoundException("Any roles found !"));
    }

    @Override
    public void saveUserRole(User user, Role role) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        System.out.println("User : " + user.getFirstName());
        System.out.println("Role : " + role.getName());
        userRoleRepository.save(userRole);
    }

    @Override
    public void deleteUserRole(Long userId, Long roleId) {
        userRoleRepository.deleteByUserIdAndRoleId(userId, roleId);
    }
}
