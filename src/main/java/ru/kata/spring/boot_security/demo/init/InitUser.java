package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.Converter;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class InitUser {
    private final UserService userService;
    private final RoleService roleService;
    private final Converter converter;

    @Autowired
    public InitUser(UserService userService, RoleService roleService, Converter converter) {
        this.userService = userService;
        this.roleService = roleService;
        this.converter = converter;
    }
    @PostConstruct
    private void createAdminAndUser() {

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");

        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);

        User admin = new User();
        admin.setFirstName("bob");
        admin.setLastName("fury");
        admin.setAge(52);
        admin.setPassword("admin");
        admin.setEmail("admin@mail.ru");
        addRoleUser(admin, roleAdmin);
        addRoleUser(admin, roleUser);
        userService.addUser(converter.convertToUserDTO(admin));

        User user = new User();
        user.setFirstName("James");
        user.setLastName("Bond");
        user.setAge(78);
        user.setPassword("user");
        user.setEmail("user@mail.ru");
        addRoleUser(user, roleUser);
        userService.addUser(converter.convertToUserDTO(user));

        User user2 = new User();
        user2.setFirstName("spanch");
        user2.setLastName("bob");
        user2.setAge(15);
        user2.setPassword("bob");
        user2.setEmail("bob@mail.ru");
        addRoleUser(user2, roleUser);
        userService.addUser(converter.convertToUserDTO(user2));

        User user3 = new User();
        user3.setFirstName("patric");
        user3.setLastName("zvezda");
        user3.setAge(16);
        user3.setPassword("patric");
        user3.setEmail("patric@mail.ru");
        addRoleUser(user3, roleUser);
        userService.addUser(converter.convertToUserDTO(user3));

        User user4 = new User();
        user4.setFirstName("tom");
        user4.setLastName("cat");
        user4.setAge(29);
        user4.setPassword("tom");
        user4.setEmail("tom@mail.ru");
        addRoleUser(user4, roleUser);
        userService.addUser(converter.convertToUserDTO(user4));

        User user5 = new User();
        user5.setFirstName("jerry");
        user5.setLastName("mouse");
        user5.setAge(6);
        user5.setPassword("jerry");
        user5.setEmail("jerry@mail.ru");
        addRoleUser(user5, roleUser);
        userService.addUser(converter.convertToUserDTO(user5));
    }

    void addRoleUser(User user, Role role) {
        user.getRoles().add(role);
    }
}
