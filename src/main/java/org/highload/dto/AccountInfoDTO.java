package org.highload.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountInfoDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String dob;
    private List<String> roles;
}
