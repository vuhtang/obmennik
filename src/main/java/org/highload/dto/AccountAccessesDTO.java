package org.highload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AccountAccessesDTO {
    private Set<String> accesses;
}
