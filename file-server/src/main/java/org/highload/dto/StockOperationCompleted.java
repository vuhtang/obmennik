package org.highload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class StockOperationCompleted {
    private Long operationAmount;
    private Long userWhoStartOperation;
    private Date dateOfOperation;
}
