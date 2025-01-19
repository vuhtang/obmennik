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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private BankingClient bankingClient;

    @Mock
    private CoinToWalletRepository coinToWalletRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuyCoinByFiatSuccess() throws WeHaveNoManeyException {
        Long id = 1L;
        Long coinIdToBuy = 1L;
        Long amount = 100L;
        Long userFiatId = 1L;

        Coin coin = new Coin();
        coin.setId(coinIdToBuy);

        StockAccountBalance stockAccountBalance = new StockAccountBalance();
        stockAccountBalance.setId(id);
        stockAccountBalance.setAmount(200L);
        stockAccountBalance.setCoin(coin);

        when(stockRepository.findById(id)).thenReturn(Optional.of(stockAccountBalance));
        when(stockRepository.save(stockAccountBalance)).thenReturn(stockAccountBalance);
        when(bankingClient.changeFiatWalletBalance(eq(id), eq("depositFiatAccount"), any(BuyCoinTransactionRequestBodyDTO.class)))
                .thenAnswer(invocation -> HttpStatus.OK);

        HttpStatus result = stockService.buyCoinByFiat(id, coinIdToBuy, amount, userFiatId);

        assertEquals(HttpStatus.OK, result);
        assertEquals(100L, stockAccountBalance.getAmount());
        verify(stockRepository, times(1)).findById(id);
        verify(stockRepository, times(1)).save(stockAccountBalance);
        verify(bankingClient, times(1)).changeFiatWalletBalance(eq(id), eq("depositFiatAccount"), any(BuyCoinTransactionRequestBodyDTO.class));
    }

    @Test
    public void testBuyCoinByFiatNoMoney() {
        Long id = 1L;
        Long coinIdToBuy = 1L;
        Long amount = 300L;
        Long userFiatId = 1L;

        Coin coin = new Coin();
        coin.setId(coinIdToBuy);

        StockAccountBalance stockAccountBalance = new StockAccountBalance();
        stockAccountBalance.setId(id);
        stockAccountBalance.setAmount(200L);
        stockAccountBalance.setCoin(coin);

        when(stockRepository.findById(id)).thenReturn(Optional.of(stockAccountBalance));

        assertThrows(WeHaveNoManeyException.class, () -> stockService.buyCoinByFiat(id, coinIdToBuy, amount, userFiatId));
        verify(stockRepository, times(1)).findById(id);
        verify(stockRepository, never()).save(any(StockAccountBalance.class));
        verify(bankingClient, never()).changeFiatWalletBalance(anyLong(), anyString(), any(BuyCoinTransactionRequestBodyDTO.class));
    }

    @Test
    public void testSellCoinByFiatSuccess() {
        Long id = 1L;
        Long accountWalletId = 1L;
        Long amount = 100L;

        Wallet wallet = new Wallet();
        wallet.setId(accountWalletId);

        CoinToWallet coinToWallet = new CoinToWallet();
        coinToWallet.setId(1);
        coinToWallet.setAmount(50L);
        coinToWallet.setWallet(wallet);

        when(coinToWalletRepository.findAll()).thenReturn(List.of(coinToWallet));
        when(coinToWalletRepository.save(coinToWallet)).thenReturn(coinToWallet);
        when(bankingClient.changeFiatWalletBalance(eq(id), eq("takeFiatAccount"), any(BuyCoinTransactionRequestBodyDTO.class)))
                .thenAnswer(invocation -> HttpStatus.OK);

        HttpStatus result = stockService.sellCoinByFiat(id, accountWalletId, amount);

        assertEquals(HttpStatus.OK, result);
        assertEquals(150L, coinToWallet.getAmount());
        verify(coinToWalletRepository, times(1)).findAll();
        verify(coinToWalletRepository, times(1)).save(coinToWallet);
        verify(bankingClient, times(1)).changeFiatWalletBalance(eq(id), eq("takeFiatAccount"), any(BuyCoinTransactionRequestBodyDTO.class));
    }
}
