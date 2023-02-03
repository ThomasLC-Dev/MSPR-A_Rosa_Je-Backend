package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.Role;
import fr.cttt.arosaje.model.dto.RoleDTO;
import fr.cttt.arosaje.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles(){
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRole(@PathVariable(name = "roleId") Long id){
        return new ResponseEntity<>(roleService.getRole(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleDTO roleDTO){
        roleService.saveRole(roleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<?> updateRole(@PathVariable(name = "roleId") Long id, @RequestBody RoleDTO roleDTO){
        roleService.updateRole(id, roleDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable(name = "roleId") Long id){
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
