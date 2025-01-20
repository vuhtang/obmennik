package org.highload.controller;

import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.highload.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StockControllerTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuyCoinByFiat() {
        Long id = 1L;
        String scriptId = "buyCoinByFiat";
        BuyCoinTransactionRequestBodyDTO requestBody = new BuyCoinTransactionRequestBodyDTO();
        requestBody.setCoinIdToBuy(1L);
        requestBody.setAmount(100L);
        requestBody.setUserFiatId(1L);

        ResponseEntity<HttpStatus> response = stockController.buyCoinByFiat(id, scriptId, requestBody);

        verify(stockService, times(1)).buyCoinByFiat(id, requestBody.getCoinIdToBuy(), requestBody.getAmount(), requestBody.getUserFiatId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSellCoinByFiat() {
        Long id = 1L;
        String scriptId = "sellCoinByFiat";
        BuyCoinTransactionRequestBodyDTO requestBody = new BuyCoinTransactionRequestBodyDTO();
        requestBody.setCoinIdToBuy(1L);
        requestBody.setAmount(100L);

        ResponseEntity<HttpStatus> response = stockController.buyCoinByFiat(id, scriptId, requestBody);

        verify(stockService, times(1)).sellCoinByFiat(id, requestBody.getCoinIdToBuy(), requestBody.getAmount());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
