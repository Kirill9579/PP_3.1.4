package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.Converter;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Converter converter;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, Converter converter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.converter = converter;
    }
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return converter.convertToUserDTO(user);
    }

    @Transactional
    public UserDTO addUser(UserDTO user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());
        userRepository.save(converter.convertToUser(user));
        return user;
    }
    @Transactional
    public List<UserDTO> getAllUsers() {
        List<UserDTO> allUsers = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            allUsers.add(converter.convertToUserDTO(user));
        }
        return allUsers;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).stream().findFirst().orElse(null);
        return converter.convertToUserDTO(user);
    }
    @Transactional
    public UserDTO removeUserById(Long id) {
        UserDTO user = getUserById(id);
        userRepository.deleteById(id);
        return user;
    }
    @Transactional
    public UserDTO updateUser(UserDTO user, Long id) {
        UserDTO userDB = getUserById(id);
        userDB.setRoles(user.getRoles());
        userDB.setFirstName(user.getFirstName());
        userDB.setLastName(user.getLastName());
        userDB.setAge(user.getAge());
        userDB.setEmail(user.getEmail());
        if (!userDB.getPassword().contains(user.getPassword())) {
            userDB.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(converter.convertToUser(userDB));
        return userDB;
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
