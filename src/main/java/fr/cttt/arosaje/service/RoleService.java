package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.Role;
import fr.cttt.arosaje.model.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Role getRole(Long id);
    void saveRole(RoleDTO roleDTO);
    void updateRole(Long id, RoleDTO roleDTO);
    void deleteRole(Long id);
}
