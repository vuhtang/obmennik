package org.highload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockOperationCompleted {
    private Long operationAmount;
    private Long userWhoStartOperation;
    private Date dateOfOperation;
}
