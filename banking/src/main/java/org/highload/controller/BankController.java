package org.highload.controller;

import lombok.RequiredArgsConstructor;
import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.highload.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/banks")
public class BankController {
    private final BankService bankService;

    @PostMapping("/{id}/scripts/{scriptId}/status")
    public ResponseEntity<HttpStatus> changeFiatWalletBalance(@PathVariable("id") Long id, @PathVariable("scriptId") String scriptId, @RequestBody BuyCoinTransactionRequestBodyDTO buyCoinTransactionRequestBodyDTO){
        switch (scriptId){
            case "depositFiatAccount":
                bankService.depositFiatAccount(buyCoinTransactionRequestBodyDTO.getUserFiatId(),buyCoinTransactionRequestBodyDTO.getAmount());
            case "takeFiatAccount":
                bankService.takeFiatAccount(buyCoinTransactionRequestBodyDTO.getUserFiatId(),buyCoinTransactionRequestBodyDTO.getAmount());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/fiat-wallets/{fiatWalletId}")
    public Mono<Long> getBalanceFromFiatWallet(@PathVariable("fiatWalletId") Long fiatWalletId) {
        return bankService.checkFiatBalanceFromWallet(fiatWalletId);
    }
}
