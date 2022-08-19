package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }



    @DeleteMapping("/admin/delete/{id}")
    public String removeUserById(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin/";
    }

    @PatchMapping("/admin/update/{id}")
    public String updateUser(@ModelAttribute("newUser") User user, @PathVariable("id") Long id) {
        userService.updateUser(user, id);
        return "redirect:/admin/";
    }

    @PostMapping("/admin/new")
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/")
    public String showOneUser(ModelMap model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<User> users = userService.getAllUsers();
        Set<Role> listRoles = new HashSet<>(roleRepository.findAll());
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("newUser", new User());
        model.addAttribute("users", users);
        model.addAttribute("authUser", user);
        return "bootstrap/AdminPanel";
    }
}
