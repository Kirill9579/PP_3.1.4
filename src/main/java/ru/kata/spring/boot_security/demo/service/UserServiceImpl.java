package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void addUser(User user) {
        if (findByEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }

    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).stream().findFirst().orElse(null);
    }
    @Transactional
    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public void updateUser(User user) {
        User userDB = getUserById(user.getId());
        userDB.setFirstName(user.getFirstName());
        userDB.setLastName(user.getLastName());
        userDB.setAge(user.getAge());
        userDB.setEmail(user.getEmail());
        userDB.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userDB);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles());
    }
}
