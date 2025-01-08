package org.highload.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuyCoinTransactionRequestBodyDTO {
    private Long amount;
    private Long coinIdToBuy;
    private Long userFiatId;
}
