package com.highload.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public record JwtTokenDto(String jwt, Long userId) {
}
