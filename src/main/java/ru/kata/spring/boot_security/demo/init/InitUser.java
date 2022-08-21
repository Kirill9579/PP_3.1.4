package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class InitUser {
    private UserService userService;
    private final RoleService roleService;

    @Autowired
    public InitUser(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @PostConstruct
    private void createAdminAndUser() {

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");

        User admin = new User();
        admin.setFirstName("bob");
        admin.setLastName("fury");
        admin.setAge(52);
        admin.setPassword("admin");
        admin.setEmail("admin@mail.ru");
        admin.getRoles().add(roleAdmin);
        admin.getRoles().add(roleUser);
        roleService.save(roleAdmin);
        roleService.save(roleUser);
        userService.addUser(admin);

        User user = new User();
        user.setFirstName("James");
        user.setLastName("Bond");
        user.setAge(78);
        user.setPassword("user");
        user.setEmail("user@mail.ru");
        user.getRoles().add(roleUser);
        userService.addUser(user);
    }
}
