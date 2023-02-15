package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.Role;
import fr.cttt.arosaje.model.User;
import fr.cttt.arosaje.model.UserRole;
import fr.cttt.arosaje.model.dto.UserRoleDTO;
import fr.cttt.arosaje.service.RoleService;
import fr.cttt.arosaje.service.UserRoleService;
import fr.cttt.arosaje.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final RoleService roleService;

    public UserRoleController(UserRoleService userRoleService, UserService userService, RoleService roleService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<UserRole>> getUserRoles(@RequestParam(name = "user", required = false) Optional<Long> userId){
        if(userId.isPresent()){
            return new ResponseEntity<>(userRoleService.getUserRolesByUser(userId.get()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(userRoleService.getUserRoles(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUserRole(@RequestBody UserRoleDTO userRoleDTO){
        User user = userService.getUser(userRoleDTO.getUserId());
        Role role = roleService.getRole(userRoleDTO.getRoleId());
        userRoleService.saveUserRole(user, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
