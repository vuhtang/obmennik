package org.highload.stock;

import org.highload.client.BankingClient;
import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.highload.exception.WeHaveNoManeyException;
import org.highload.model.Coin;
import org.highload.model.CoinToWallet;
import org.highload.model.StockAccountBalance;
import org.highload.model.Wallet;
import org.highload.repository.CoinToWalletRepository;
import org.highload.repository.StockRepository;
import org.highload.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private BankingClient bankingClient;

    @Mock
    private CoinToWalletRepository coinToWalletRepository;

    @InjectMocks
    private StockService stockService;

    private StockAccountBalance stockAccountBalance;
    private CoinToWallet coinToWallet;
    private Coin coin;
    private Wallet wallet;

    @BeforeEach
    public void setUp() {
        coin = new Coin();
        coin.setId(1L);

        wallet = new Wallet();
        wallet.setId(1L);

        stockAccountBalance = new StockAccountBalance();
        stockAccountBalance.setId(1L);
        stockAccountBalance.setCoin(coin);
        stockAccountBalance.setAmount(100L);

        coinToWallet = new CoinToWallet();
        coinToWallet.setId(1);
        coinToWallet.setCoin(coin);
        coinToWallet.setWallet(wallet);
        coinToWallet.setAmount(50L);
    }

    @Test
    public void testBuyCoinByFiat_Success() throws WeHaveNoManeyException {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stockAccountBalance));

        stockService.buyCoinByFiat(1L, 1L, 50L, 1L);

        verify(stockRepository, times(1)).save(any(StockAccountBalance.class));
        verify(bankingClient, times(1)).changeFiatWalletBalance(
                eq(1L),
                eq("depositFiatAccount"),
                any(BuyCoinTransactionRequestBodyDTO.class)
        );
    }

    @Test
    public void testBuyCoinByFiat_NoMoneyException() {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stockAccountBalance));

        assertThrows(WeHaveNoManeyException.class, () -> {
            stockService.buyCoinByFiat(1L, 1L, 150L, 1L);
        });

        verify(stockRepository, never()).save(any(StockAccountBalance.class));
        verify(bankingClient, never()).changeFiatWalletBalance(anyLong(), anyString(), any(BuyCoinTransactionRequestBodyDTO.class));
    }

    @Test
    public void testSellCoinByFiat_Success() {
        when(coinToWalletRepository.findAll()).thenReturn(Stream.of(coinToWallet).toList());

        stockService.sellCoinByFiat(1L, 1L, 50L);

        verify(coinToWalletRepository, times(1)).save(any(CoinToWallet.class));
        verify(bankingClient, times(1)).changeFiatWalletBalance(
                eq(1L),
                eq("takeFiatAccount"),
                any(BuyCoinTransactionRequestBodyDTO.class)
        );
    }
}
