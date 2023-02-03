package fr.cttt.arosaje.controller;

import fr.cttt.arosaje.model.UserRole;
import fr.cttt.arosaje.service.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
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
}
