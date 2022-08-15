package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class InitUser {
    private UserService userService;

    @Autowired
    public InitUser(UserService userService) {
        this.userService = userService;
        createAdmin();
    }
    private void createAdmin() {

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@mail.ru");
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        admin.getRoles().add(roleAdmin);
        userService.addUser(admin);
    }
}
