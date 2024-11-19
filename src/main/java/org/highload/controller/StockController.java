package org.highload.controller;

import lombok.RequiredArgsConstructor;
import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.highload.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
//    TODO name of method
    @PostMapping("/{id}/buyCoinByFiat")
    public ResponseEntity<HttpStatus> buyCoinByFiat(@PathVariable("id") Long id, @RequestBody BuyCoinTransactionRequestBodyDTO buyCoinTransactionRequestBodyDTO){
        stockService.buyCoinByFiat(
                id,
                buyCoinTransactionRequestBodyDTO.getCoinIdToBuy(),
                buyCoinTransactionRequestBodyDTO.getAmount(),
                buyCoinTransactionRequestBodyDTO.getUserFiatId()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/sellCoinByFiat")
    public ResponseEntity<HttpStatus> sellCoinByFiat(@PathVariable("id") Long id, @RequestBody BuyCoinTransactionRequestBodyDTO buyCoinTransactionRequestBodyDTO){
        stockService.sellCoinByFiat(
                id,
                buyCoinTransactionRequestBodyDTO.getCoinIdToBuy(),
                buyCoinTransactionRequestBodyDTO.getAmount()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
