package org.highload.stock;

import org.highload.controller.StockController;
import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.highload.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class StockControllerTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuyCoinByFiat() {
        Long id = 1L;
        String scriptId = "buyCoinByFiat";
        BuyCoinTransactionRequestBodyDTO requestBody = BuyCoinTransactionRequestBodyDTO.builder()
                .amount(12L)
                .coinIdToBuy(1L)
                .userFiatId(2L)
                .build();

        when(stockService.buyCoinByFiat(id, requestBody.getCoinIdToBuy(), requestBody.getAmount(), requestBody.getUserFiatId()))
                .thenReturn(HttpStatus.OK);

        Mono<ResponseEntity<HttpStatus>> responseMono = stockController.buyCoinByFiat(id, scriptId, requestBody);

        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getStatusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(stockService, times(1)).buyCoinByFiat(id, requestBody.getCoinIdToBuy(), requestBody.getAmount(), requestBody.getUserFiatId());
    }

    @Test
    public void testSellCoinByFiat() {
        Long id = 1L;
        String scriptId = "sellCoinByFiat";
        BuyCoinTransactionRequestBodyDTO requestBody = BuyCoinTransactionRequestBodyDTO.builder()
                .amount(12L)
                .coinIdToBuy(1L)
                .userFiatId(2L)
                .build();

        when(stockService.sellCoinByFiat(id, requestBody.getCoinIdToBuy(), requestBody.getAmount()))
                .thenReturn(HttpStatus.OK);

        Mono<ResponseEntity<HttpStatus>> responseMono = stockController.buyCoinByFiat(id, scriptId, requestBody);

        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getStatusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(stockService, times(1)).sellCoinByFiat(id, requestBody.getCoinIdToBuy(), requestBody.getAmount());
    }

    @Test
    public void testInvalidScriptId() {
        Long id = 1L;
        String scriptId = "invalidScriptId";
        BuyCoinTransactionRequestBodyDTO requestBody = BuyCoinTransactionRequestBodyDTO.builder()
                .amount(12L)
                .coinIdToBuy(1L)
                .userFiatId(2L)
                .build();

        Mono<ResponseEntity<HttpStatus>> responseMono = stockController.buyCoinByFiat(id, scriptId, requestBody);

        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getStatusCode().equals(HttpStatus.BAD_REQUEST))
                .verifyComplete();
    }
}
