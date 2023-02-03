package fr.cttt.arosaje.service.impl;

import fr.cttt.arosaje.exception.ElementNotFoundException;
import fr.cttt.arosaje.model.Role;
import fr.cttt.arosaje.model.dto.RoleDTO;
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
    public void saveRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        roleRepository.save(role);
    }

    @Override
    public void updateRole(Long id, RoleDTO roleDTO) {
        Role role = this.getRole(id);
        role.setName((roleDTO.getName() == null) ? role.getName() : roleDTO.getName());
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
