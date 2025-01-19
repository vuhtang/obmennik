package com.highload.auth.service;

import com.highload.auth.exeptions.AuthenticationException;
import com.highload.auth.exeptions.UserAlreadyPresentException;
import com.highload.auth.model.dto.JwtTokenDto;
import com.highload.auth.model.dto.UserRegisterRequestDto;
import com.highload.auth.model.dto.UserRequestDto;
import com.highload.auth.model.entity.User;
import com.highload.auth.model.entity.roles.UserRole;
import com.highload.auth.repository.RolesRepository;
import com.highload.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtTokenDto login(UserRequestDto request) throws AuthenticationException {
        User user = userRepository.findUserByEmail(request.email()).orElseThrow();
        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new AuthenticationException("Invalid email or password");
        List<String> roles = userRepository.findUserRolesNames(user.getId());

        user.setRoles(roles.stream().map(name -> {
            UserRole role = new UserRole();
            role.setName(name);
            return role;
        }).collect(Collectors.toSet()));

        String jwt = jwtService.generateToken(user);
        return new JwtTokenDto(jwt, user.getId());
    }

    public JwtTokenDto register(UserRegisterRequestDto request) throws UserAlreadyPresentException {
        if (userRepository.findUserByEmail(request.email()).isPresent())
            throw new UserAlreadyPresentException("User with such email is already presented");

        Optional<UserRole> userRole = rolesRepository.findUserRoleByName("user");
        if (userRole.isEmpty())
            throw new RuntimeException();

        Set<UserRole> roles = new HashSet<>();
        roles.add(userRole.get());
        User user = User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(roles)
                .dateOfBirth(Date.valueOf(request.dateOfBirth()))
                .build();
        userRepository.saveAndFlush(user);

        String jwt = jwtService.generateToken(user);
        return new JwtTokenDto(jwt, user.getId());
    }
}
