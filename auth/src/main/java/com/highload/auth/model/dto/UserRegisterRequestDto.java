package com.highload.auth.model.dto;

public record UserRegisterRequestDto(String name, String surname, String email, String password, String dateOfBirth) {
}
