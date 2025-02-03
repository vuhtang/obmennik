package org.highload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoWalletHasChange {
    private Long operationAmount;
    private Long walletId;
    private Date dateOfOperation;
}
