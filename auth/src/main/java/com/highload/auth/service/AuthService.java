package com.highload.auth.service;

import com.highload.auth.exeptions.UserAlreadyPresentException;
import com.highload.auth.mappers.UserRequestMapper;
import com.highload.auth.model.dto.JwtTokenDto;
import com.highload.auth.model.dto.UserRegisterRequestDto;
import com.highload.auth.model.dto.UserRequestDto;
import com.highload.auth.model.entity.User;
import com.highload.auth.model.entity.roles.UserRole;
import com.highload.auth.repository.RolesRepository;
import com.highload.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtTokenDto login(UserRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));

        User user = userRepository.findUserByEmail(request.email()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        return new JwtTokenDto(jwt, user.getId());
    }

    public JwtTokenDto register(UserRegisterRequestDto request) throws UserAlreadyPresentException {
        if (userRepository.findUserByEmail(request.email()).isPresent())
            throw new UserAlreadyPresentException();

        Optional<UserRole> userRole = rolesRepository.findUserRoleByName("user");
        if (userRole.isEmpty())
            throw new RuntimeException();

        Set<UserRole> roles = new HashSet<>();
        roles.add(userRole.get());
        User user = userRepository.saveAndFlush(User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(roles)
                .build());

        String jwt = jwtService.generateToken(user);
        return new JwtTokenDto(jwt, user.getId());
    }
}
