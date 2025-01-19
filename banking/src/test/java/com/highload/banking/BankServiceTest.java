package com.highload.banking;

import org.highload.exception.ResourceNotFoundException;
import org.highload.model.FiatWallet;
import org.highload.repository.FiatRepository;
import org.highload.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BankServiceTest {

    @Mock
    private FiatRepository fiatRepository;

    @InjectMocks
    private BankService bankService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDepositFiatAccount() {
        Long fiatAccountId = 1L;
        Long depositAmount = 100L;
        FiatWallet fiatWallet = new FiatWallet();
        fiatWallet.setBalance(500L);

        when(fiatRepository.findById(fiatAccountId)).thenReturn(Mono.just(fiatWallet));
        when(fiatRepository.updateBalanceById(fiatAccountId, 600L)).thenReturn(Mono.empty());

        bankService.depositFiatAccount(fiatAccountId, depositAmount);

        verify(fiatRepository, times(1)).findById(fiatAccountId);
        verify(fiatRepository, times(1)).updateBalanceById(fiatAccountId, 600L);
    }

    @Test
    public void testTakeFiatAccount() {
        Long fiatAccountId = 1L;
        Long takeAmount = 100L;
        FiatWallet fiatWallet = new FiatWallet();
        fiatWallet.setBalance(500L);

        when(fiatRepository.findById(fiatAccountId)).thenReturn(Mono.just(fiatWallet));
        when(fiatRepository.updateBalanceById(fiatAccountId, 400L)).thenReturn(Mono.empty());

        bankService.takeFiatAccount(fiatAccountId, takeAmount);

        verify(fiatRepository, times(1)).findById(fiatAccountId);
        verify(fiatRepository, times(1)).updateBalanceById(fiatAccountId, 400L);
    }

    @Test
    public void testTakeFiatAccountNotFound() {
        Long fiatAccountId = 1L;
        Long takeAmount = 100L;

        when(fiatRepository.findById(fiatAccountId)).thenReturn(Mono.empty());

        assertThrows(ResourceNotFoundException.class, () -> bankService.takeFiatAccount(fiatAccountId, takeAmount));

        verify(fiatRepository, times(1)).findById(fiatAccountId);
    }

    @Test
    public void testCheckFiatBalanceFromWallet() {
        Long fiatWalletId = 1L;
        FiatWallet fiatWallet = new FiatWallet();
        fiatWallet.setBalance(500L);

        when(fiatRepository.findById(fiatWalletId)).thenReturn(Mono.just(fiatWallet));

        Mono<Long> balanceMono = bankService.checkFiatBalanceFromWallet(fiatWalletId);

        balanceMono.subscribe(balance -> assertEquals(500L, balance));

        verify(fiatRepository, times(1)).findById(fiatWalletId);
    }

    @Test
    public void testCheckFiatBalanceFromWalletNotFound() {
        Long fiatWalletId = 1L;

        when(fiatRepository.findById(fiatWalletId)).thenReturn(Mono.empty());

        Mono<Long> balanceMono = bankService.checkFiatBalanceFromWallet(fiatWalletId);

        balanceMono.subscribe(
                balance -> {},
                error -> assertEquals(ResourceNotFoundException.class, error.getClass())
        );

        verify(fiatRepository, times(1)).findById(fiatWalletId);
    }
}
