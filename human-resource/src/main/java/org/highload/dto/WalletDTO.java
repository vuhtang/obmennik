package org.highload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WalletDTO {
    private Long id;
    private List<CoinDTO> coins;
//    private List<Wallet> wallets;

    @Data
    @AllArgsConstructor
    public static class CoinDTO {
        public String coinType;
        public Long amount;
    }

}
