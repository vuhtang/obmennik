package org.highload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountAccessesDTO {
    private List<String> accesses;
}
