package org.highload.controller;

import lombok.RequiredArgsConstructor;

import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.highload.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
    @PostMapping("/{id}/scripts/{scriptId}/status")
    public Mono<ResponseEntity<HttpStatus>> buyCoinByFiat(@PathVariable("id") Long id, @PathVariable("scriptId") String scriptId, @RequestBody BuyCoinTransactionRequestBodyDTO buyCoinTransactionRequestBodyDTO){
        switch (scriptId) {
            case "buyCoinByFiat":
                return Mono.just(
                        new ResponseEntity<>(
                                stockService.buyCoinByFiat(
                                    id,
                                    buyCoinTransactionRequestBodyDTO.getCoinIdToBuy(),
                                    buyCoinTransactionRequestBodyDTO.getAmount(),
                                    buyCoinTransactionRequestBodyDTO.getUserFiatId()
                                )
                        )
                );
            case "sellCoinByFiat":
                return Mono.just(
                        new ResponseEntity<>(
                                stockService.sellCoinByFiat(
                                        id,
                                        buyCoinTransactionRequestBodyDTO.getCoinIdToBuy(),
                                        buyCoinTransactionRequestBodyDTO.getAmount()
                                )
                        )
                );
        }
        return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
