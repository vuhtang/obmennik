package com.highload.auth.controller;

import com.highload.auth.exeptions.UserAlreadyPresentException;
import com.highload.auth.model.dto.JwtTokenDto;
import com.highload.auth.model.dto.UserRegisterRequestDto;
import com.highload.auth.model.dto.UserRequestDto;
import com.highload.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtTokenDto> register(@RequestBody UserRegisterRequestDto request) throws UserAlreadyPresentException {
        return ResponseEntity.ok(authService.register(request));
    }
}
