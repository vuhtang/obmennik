package org.highload.dto;

import lombok.Data;

@Data
public class SellCoinTransactionRequestBodyDTO {
    private Long amount;
    private Long coinIdToBuy;
}
