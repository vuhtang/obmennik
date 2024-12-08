package org.highload.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.highload.model.stock.FiatWallet;
import org.highload.repository.FiatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private FiatRepository fiatRepository;

    @InjectMocks
    private BankService bankService;

    private FiatWallet fiatWallet;

    @BeforeEach
    public void setUp() {
        fiatWallet = new FiatWallet();
        fiatWallet.setBalance(100L);
    }

    @Test
    public void testDepositFiatAccount() {
        Long fiatAccountId = 1L;
        Long depositAmount = 50L;

        when(fiatRepository.findById(fiatAccountId)).thenReturn(Optional.of(fiatWallet));
        bankService.depositFiatAccount(fiatAccountId, depositAmount);
        assertEquals(Long.valueOf(150), fiatWallet.getBalance());

        verify(fiatRepository).save(fiatWallet);
    }

    @Test
    public void testDepositFiatAccountNotFound() {
        Long fiatAccountId = 1L;
        Long depositAmount = 50L;

        when(fiatRepository.findById(fiatAccountId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            bankService.depositFiatAccount(fiatAccountId, depositAmount);
        });
    }

    @Test
    public void testTakeFiatAccount() {
        Long fiatAccountId = 1L;
        Long takeAmount = 30L;

        when(fiatRepository.findById(fiatAccountId)).thenReturn(Optional.of(fiatWallet));

        bankService.takeFiatAccount(fiatAccountId, takeAmount);

        assertEquals(Long.valueOf(70), fiatWallet.getBalance());

        verify(fiatRepository).save(fiatWallet);
    }

    @Test
    public void testTakeFiatAccountNotFound() {
        Long fiatAccountId = 1L;
        Long takeAmount = 30L;

        when(fiatRepository.findById(fiatAccountId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            bankService.takeFiatAccount(fiatAccountId, takeAmount);
        });
    }
}