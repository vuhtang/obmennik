package org.highload.stock;

import org.highload.controller.StockController;
import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.highload.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    private BuyCoinTransactionRequestBodyDTO buyCoinTransactionRequestBodyDTO;

    @BeforeEach
    public void setUp() {
        buyCoinTransactionRequestBodyDTO = BuyCoinTransactionRequestBodyDTO.builder()
                .coinIdToBuy(1L)
                .amount(50L)
                .userFiatId(1L)
                .build();
    }

    @Test
    public void testBuyCoinByFiat_Success() {
        ResponseEntity<HttpStatus> response = stockController.buyCoinByFiat(1L, "buyCoinByFiat", buyCoinTransactionRequestBodyDTO);

        verify(stockService, times(1)).buyCoinByFiat(1L, 1L, 50L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSellCoinByFiat_Success() {
        ResponseEntity<HttpStatus> response = stockController.buyCoinByFiat(1L, "sellCoinByFiat", buyCoinTransactionRequestBodyDTO);

        verify(stockService, times(1)).sellCoinByFiat(1L, 1L, 50L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
