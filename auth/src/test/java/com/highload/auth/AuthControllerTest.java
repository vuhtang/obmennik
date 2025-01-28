package com.highload.auth;

import com.highload.auth.controller.AuthController;
import com.highload.auth.exeptions.AuthenticationException;
import com.highload.auth.exeptions.UserAlreadyPresentException;
import com.highload.auth.model.dto.JwtTokenDto;
import com.highload.auth.model.dto.UserRegisterRequestDto;
import com.highload.auth.model.dto.UserRequestDto;
import com.highload.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccess() throws AuthenticationException {
        UserRequestDto request = new UserRequestDto("username", "password");
        JwtTokenDto tokenDto = new JwtTokenDto("token", 1L);

        when(authService.login(request)).thenReturn(tokenDto);

        ResponseEntity<JwtTokenDto> response = authController.login(request);

        assertEquals(ResponseEntity.ok(tokenDto), response);
        verify(authService, times(1)).login(request);
    }

    @Test
    public void testLoginFailure() throws AuthenticationException {
        UserRequestDto request = new UserRequestDto("username", "password");

        when(authService.login(request)).thenThrow(new AuthenticationException("Invalid credentials"));

        assertThrows(AuthenticationException.class, () -> authController.login(request));
        verify(authService, times(1)).login(request);
    }

    @Test
    public void testRegisterSuccess() throws UserAlreadyPresentException {
        UserRegisterRequestDto request = new UserRegisterRequestDto(
                "name", "surname", "email@mail.ru",
                "12345", "2022-12-12"
        );
        JwtTokenDto tokenDto = new JwtTokenDto("token", 1L);

        when(authService.register(request)).thenReturn(tokenDto);

        ResponseEntity<JwtTokenDto> response = authController.register(request);

        assertEquals(ResponseEntity.ok(tokenDto), response);
        verify(authService, times(1)).register(request);
    }

    @Test
    public void testRegisterFailure() throws UserAlreadyPresentException {
        UserRegisterRequestDto request = new UserRegisterRequestDto(
                "name", "surname", "email@mail.ru",
                "12345", "2022-12-12"
        );
        when(authService.register(request)).thenThrow(new UserAlreadyPresentException("User already present"));

        assertThrows(UserAlreadyPresentException.class, () -> authController.register(request));
        verify(authService, times(1)).register(request);
    }
}
