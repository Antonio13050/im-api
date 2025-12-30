package com.im_api.controller;

import com.im_api.dto.UserCreateDTO;
import com.im_api.model.User;
import com.im_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> list(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        List<User> users = userService.findUsersByRole(userId, role);
        return ResponseEntity.ok(users);
    }




    @PostMapping()
    public ResponseEntity<User> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User user = userService.create(userCreateDTO);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody UserCreateDTO userUpdateDTO) {
        User user = userService.update(id, userUpdateDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
