package com.htlkaindorf.absences_api.bootstrap;

import com.htlkaindorf.absences_api.dtos.RegisterUserDto;
import com.htlkaindorf.absences_api.entities.Role;
import com.htlkaindorf.absences_api.entities.User;
import com.htlkaindorf.absences_api.enums.RoleEnum;
import com.htlkaindorf.absences_api.repositories.RoleRepository;
import com.htlkaindorf.absences_api.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setFullName("Super Admin");
        userDto.setEmail("super.admin@email.com");
        userDto.setPassword("123456");

        // Ensure the role exists
        Role role = roleRepository.findByName(RoleEnum.SUPER_ADMIN)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(RoleEnum.SUPER_ADMIN);
                    newRole.setDescription("Super administrator with full access"); // Set description here
                    return roleRepository.save(newRole);
                });

        // Check if the user already exists
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            return;
        }

        // Create the user if it doesn't exist
        var user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);

        userRepository.save(user);
    }
}
