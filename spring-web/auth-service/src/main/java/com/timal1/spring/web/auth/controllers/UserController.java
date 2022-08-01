package com.timal1.spring.web.auth.controllers;


import com.timal1.spring.web.auth.entities.User;
import com.timal1.spring.web.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all_users")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/super_admin")
    // @PreAuthorize("hasRole('ADMIN')")
    public String adminPage() {
        return "hello_super_admin";
    }

    @GetMapping("/user_info")
    public String daoTestPage(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Unable to find user by username: " + principal.getName()));
        return "Authenticated user info: " + user.getUsername() + " : " + user.getEmail() + " : " + user.getRoles();
    }
}
