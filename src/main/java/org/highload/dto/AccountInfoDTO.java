package org.highload.dto;

import lombok.Data;

@Data
public class AccountInfoDTO {
    private String name;
    private String surname;
    private String email;
    private String role;
    private int roleId;
}
