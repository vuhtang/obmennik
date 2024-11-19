package org.highload.service;

import org.highload.exceptions.WeHaveNoManeyException;
import org.highload.model.stock.Coin;
import org.highload.model.stock.CoinToWallet;
import org.highload.model.stock.StockAccountBalance;
import org.highload.model.stock.Wallet;
import org.highload.repository.CoinToWalletRepository;
import org.highload.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private BankService bankService;

    @Mock
    private CoinToWalletRepository coinToWalletRepository;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuyCoinByFiat_Success() {
        Long stockId = 1L;
        Long coinIdToBuy = 100L;
        Long amount = 50L;
        Long userFiatId = 200L;

        StockAccountBalance stockAccountBalance = new StockAccountBalance();
        stockAccountBalance.setId(stockId);
        stockAccountBalance.setAmount(100L);
        stockAccountBalance.setCoin(new Coin());
        stockAccountBalance.getCoin().setId(coinIdToBuy);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stockAccountBalance));

        stockService.buyCoinByFiat(stockId, coinIdToBuy, amount, userFiatId);

        assertEquals(50L, stockAccountBalance.getAmount());
        verify(stockRepository, times(1)).save(stockAccountBalance);
        verify(bankService, times(1)).depositFiatAccount(userFiatId, amount);
    }

    @Test
    void testBuyCoinByFiat_NotEnoughStockBalance() {
        Long stockId = 1L;
        Long coinIdToBuy = 100L;
        Long amount = 150L; // more than available
        Long userFiatId = 200L;

        StockAccountBalance stockAccountBalance = new StockAccountBalance();
        stockAccountBalance.setId(stockId);
        stockAccountBalance.setAmount(100L);
        stockAccountBalance.setCoin(new Coin());
        stockAccountBalance.getCoin().setId(coinIdToBuy);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stockAccountBalance));

        WeHaveNoManeyException exception = assertThrows(WeHaveNoManeyException.class, () ->
                stockService.buyCoinByFiat(stockId, coinIdToBuy, amount, userFiatId)
        );

        assertNotNull(exception.getMessage());

        assertEquals("We have no money, sorry :((", exception.getMessage());
        verify(stockRepository, never()).save(any());
        verify(bankService, never()).depositFiatAccount(anyLong(), anyLong());
    }

    @Test
    void testSellCoinByFiat_Success() {
        Long stockId = 1L;
        Long accountWalletId = 200L;
        Long amount = 50L;

        CoinToWallet coinToWallet = new CoinToWallet();
        coinToWallet.setAmount(100L);
        coinToWallet.setWallet(new Wallet());
        coinToWallet.getWallet().setId(accountWalletId);

        when(coinToWalletRepository.findAll()).thenReturn(List.of(coinToWallet));

        stockService.sellCoinByFiat(stockId, accountWalletId, amount);

        assertEquals(150L, coinToWallet.getAmount());
        verify(coinToWalletRepository, times(1)).save(coinToWallet);
        verify(bankService, times(1)).takeFiatAccount(StockService.STOCK_FIAT_ACCOUNT_ID, amount);
    }

    @Test
    void testSellCoinByFiat_NoWalletFound() {
        Long stockId = 1L;
        Long accountWalletId = 200L;
        Long amount = 50L;

        when(coinToWalletRepository.findAll()).thenReturn(List.of());

        assertThrows(NoSuchElementException.class, () ->
                stockService.sellCoinByFiat(stockId, accountWalletId, amount)
        );

        verify(coinToWalletRepository, never()).save(any());
        verify(bankService, never()).takeFiatAccount(anyLong(), anyLong());
    }
}
