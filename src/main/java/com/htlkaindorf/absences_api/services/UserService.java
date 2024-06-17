package com.htlkaindorf.absences_api.services;


import com.htlkaindorf.absences_api.dtos.RegisterUserDto;
import com.htlkaindorf.absences_api.entities.Role;
import com.htlkaindorf.absences_api.entities.User;
import com.htlkaindorf.absences_api.enums.RoleEnum;
import com.htlkaindorf.absences_api.repositories.RoleRepository;
import com.htlkaindorf.absences_api.repositories.UserRepository;
import org.springframework.stereotype.Service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    public User createAdministrator(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setFullName(input.getFullName());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());
        user.setEmail(input.getEmail());

        return userRepository.save(user);
    }
}
