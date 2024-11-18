package org.highload.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountShortInfoDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
}
