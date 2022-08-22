package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@Controller
public class UsersController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    @Autowired
    public UsersController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String login() {
        return "redirect:/login";
    }
    @GetMapping("/user/")
    public String getOneUser(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("listRole", user.getRoles());
        model.addAttribute("user", user);
        return "bootstrap/UserPage";
    }
}
