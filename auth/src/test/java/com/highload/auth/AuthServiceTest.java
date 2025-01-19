package com.highload.auth;

import com.highload.auth.exeptions.AuthenticationException;
import com.highload.auth.exeptions.UserAlreadyPresentException;
import com.highload.auth.model.dto.JwtTokenDto;
import com.highload.auth.model.dto.UserRegisterRequestDto;
import com.highload.auth.model.dto.UserRequestDto;
import com.highload.auth.model.entity.User;
import com.highload.auth.model.entity.roles.UserRole;
import com.highload.auth.repository.RolesRepository;
import com.highload.auth.repository.UserRepository;
import com.highload.auth.service.AuthService;
import com.highload.auth.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccess() throws AuthenticationException {
        UserRequestDto request = new UserRequestDto("test@example.com", "password");
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .build();
        List<String> roles = List.of("ROLE_USER");

        when(userRepository.findUserByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.password(), user.getPassword())).thenReturn(true);
        when(userRepository.findUserRolesNames(user.getId())).thenReturn(roles);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        JwtTokenDto tokenDto = authService.login(request);

        assertEquals("jwtToken", tokenDto.jwt());
        assertEquals(1L, tokenDto.userId());
        verify(userRepository, times(1)).findUserByEmail(request.email());
        verify(passwordEncoder, times(1)).matches(request.password(), user.getPassword());
        verify(userRepository, times(1)).findUserRolesNames(user.getId());
        verify(jwtService, times(1)).generateToken(user);
    }

    @Test
    public void testLoginFailure() {
        UserRequestDto request = new UserRequestDto("test@example.com", "wrongPassword");
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findUserByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.password(), user.getPassword())).thenReturn(false);

        assertThrows(AuthenticationException.class, () -> authService.login(request));
        verify(userRepository, times(1)).findUserByEmail(request.email());
        verify(passwordEncoder, times(1)).matches(request.password(), user.getPassword());
    }

    @Test
    public void testRegisterSuccess() throws UserAlreadyPresentException {
        UserRegisterRequestDto request = new UserRegisterRequestDto("name", "surname", "mail@mail.com", "password", "1990-01-01");
        UserRole userRole = new UserRole();
        userRole.setName("user");
        User user = User.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .email("mail@mail.com")
                .password("encodedPassword")
                .dateOfBirth(Date.valueOf("1990-01-01"))
                .build();

        when(userRepository.findUserByEmail(request.email())).thenReturn(Optional.empty());
        when(rolesRepository.findUserRoleByName("user")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        JwtTokenDto tokenDto = authService.register(request);

        verify(userRepository, times(1)).findUserByEmail(request.email());
        verify(rolesRepository, times(1)).findUserRoleByName("user");
        verify(passwordEncoder, times(1)).encode(request.password());
        verify(userRepository, times(1)).saveAndFlush(any(User.class));
    }

    @Test
    public void testRegisterFailure() {
        UserRegisterRequestDto request = new UserRegisterRequestDto("John", "Doe", "test@example.com", "password", "1990-01-01");
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        when(userRepository.findUserByEmail(request.email())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyPresentException.class, () -> authService.register(request));
        verify(userRepository, times(1)).findUserByEmail(request.email());
    }
}
