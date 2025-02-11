package com.highload.banking;

import org.highload.controller.BankController;
import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.highload.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BankControllerTest {

    @Mock
    private BankService bankService;

    @InjectMocks
    private BankController bankController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testChangeFiatWalletBalanceDeposit() {
        BuyCoinTransactionRequestBodyDTO requestBody = new BuyCoinTransactionRequestBodyDTO();
        requestBody.setAmount(10L);
        requestBody.setUserFiatId(1L);
        requestBody.setCoinIdToBuy(2L);

        Long id = 1L;
        String scriptId = "depositFiatAccount";

        doNothing().when(bankService).depositFiatAccount(requestBody.getUserFiatId(), requestBody.getAmount());

        ResponseEntity<HttpStatus> response = bankController.changeFiatWalletBalance(id, scriptId, requestBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bankService, times(1)).depositFiatAccount(requestBody.getUserFiatId(), requestBody.getAmount());
    }

    @Test
    public void testChangeFiatWalletBalanceTake() {
        BuyCoinTransactionRequestBodyDTO requestBody = new BuyCoinTransactionRequestBodyDTO();
        requestBody.setAmount(10L);
        requestBody.setUserFiatId(1L);
        requestBody.setCoinIdToBuy(2L);

        Long id = 1L;
        String scriptId = "takeFiatAccount";

        doNothing().when(bankService).takeFiatAccount(requestBody.getUserFiatId(), requestBody.getAmount());

        ResponseEntity<HttpStatus> response = bankController.changeFiatWalletBalance(id, scriptId, requestBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bankService, times(1)).takeFiatAccount(requestBody.getUserFiatId(), requestBody.getAmount());
    }

    @Test
    public void testGetBalanceFromFiatWallet() {
        Long fiatWalletId = 1L;
        Long balance = 1000L;

        when(bankService.checkFiatBalanceFromWallet(fiatWalletId)).thenReturn(Mono.just(balance));

        Mono<Long> result = bankController.getBalanceFromFiatWallet(1L, fiatWalletId);

        result.subscribe(actualBalance -> assertEquals(balance, actualBalance));
        verify(bankService, times(1)).checkFiatBalanceFromWallet(fiatWalletId);
    }
}
