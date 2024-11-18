package org.highload.dto;

import java.util.List;

public class WalletDTO {
    private int id;
    private List<CoinDTO> coins;
    public static class CoinDTO {
        public String coinType;
        public int amount;
    }

}
