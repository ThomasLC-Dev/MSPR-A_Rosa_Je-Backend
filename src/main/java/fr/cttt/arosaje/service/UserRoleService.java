package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.Role;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.UserRole;
import fr.cttt.arosaje.model.dto.UserRoleDTO;

import java.util.List;

public interface UserRoleService {
    List<UserRole> getUserRoles();
    List<UserRole> getUserRolesByUser(Long userId);
    void saveUserRole(User user, Role role);
    void deleteUserRole(Long userId, Long roleid);
}
