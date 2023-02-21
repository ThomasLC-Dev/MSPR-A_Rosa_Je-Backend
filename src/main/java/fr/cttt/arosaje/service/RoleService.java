package fr.cttt.arosaje.service;

import fr.cttt.arosaje.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Role getRole(Long id);
    Role getRoleByName(String name);
    void saveRole(Role role);
}