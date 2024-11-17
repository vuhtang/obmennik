package org.highload.dto;

import lombok.Data;

@Data
public class BuyCoinTransactionRequestBodyDTO {
    private Long amount;
    private Long coinIdToBuy;
    private Long userFiatId;
}
