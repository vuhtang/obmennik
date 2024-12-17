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

    @PostMapping("/{id}/scripts/{scriptId}/status")
    public ResponseEntity<HttpStatus> buyCoinByFiat(@PathVariable("id") Long id,@PathVariable("scriptId") String scriptId,@RequestBody BuyCoinTransactionRequestBodyDTO buyCoinTransactionRequestBodyDTO){
        switch (scriptId){
            case "buyCoinByFiat":
                stockService.buyCoinByFiat(
                        id,
                        buyCoinTransactionRequestBodyDTO.getCoinIdToBuy(),
                        buyCoinTransactionRequestBodyDTO.getAmount(),
                        buyCoinTransactionRequestBodyDTO.getUserFiatId()
                );
            case "sellCoinByFiat":
                stockService.sellCoinByFiat(
                        id,
                        buyCoinTransactionRequestBodyDTO.getCoinIdToBuy(),
                        buyCoinTransactionRequestBodyDTO.getAmount()
                );
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/testFeign")
//    public ResponseEntity<AccountShortInfoDTO> test(){
//        return ResponseEntity.ok(AccountShortInfoDTO.builder().email("a").build());
//    }
}
