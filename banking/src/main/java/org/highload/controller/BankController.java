package org.highload.controller;

import lombok.RequiredArgsConstructor;
import org.highload.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/banks")
public class BankController {
    private final BankService bankService;

    @GetMapping("/{id}/fiat-wallets/{fiatWalletId}")
    public Mono<Long> getBalanceFromFiatWallet(@PathVariable("fiatWalletId") Long fiatWalletId) {
//        return Mono.just(ResponseEntity.of(bankService.checkFiatBalanceFromWallet(fiatWalletId)));
        return bankService.checkFiatBalanceFromWallet(fiatWalletId);
    }
}
