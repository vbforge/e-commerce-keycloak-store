package com.vbforge.client.service;

import com.vbforge.client.dto.RegisterDto;
import com.vbforge.client.entity.Role;
import com.vbforge.client.entity.User;
import com.vbforge.client.exception.OrderException;
import com.vbforge.client.repository.RoleRepository;
import com.vbforge.client.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new OrderException("Username '" + dto.getUsername() + "' is already taken");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new OrderException("Email '" + dto.getEmail() + "' is already registered");
        }
        if (!dto.passwordsMatch()) {
            throw new OrderException("Passwords do not match");
        }

        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
            .orElseThrow(() -> new OrderException("Default role not found — check data initializer"));

        User user = User.builder()
            .username(dto.getUsername())
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .roles(Set.of(userRole))
            .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new OrderException("User not found: " + username));
    }
}