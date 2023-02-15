package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.Role;
import fr.cttt.arosaje.repository.RoleRepository;
import fr.cttt.arosaje.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Role not found !"));
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new ElementNotFoundException("Role not found"));
    }
}