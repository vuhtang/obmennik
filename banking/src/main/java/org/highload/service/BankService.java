package org.highload.service;

import lombok.RequiredArgsConstructor;
import org.highload.exception.ResourceNotFoundException;
import org.highload.model.FiatWallet;
import org.highload.repository.FiatRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {
    private final FiatRepository fiatRepository;
    //TODO tests

    public void depositFiatAccount(Long fiatAccountId, Long depositAmount) {
        FiatWallet fiatWallet = fiatRepository.findById(fiatAccountId).blockOptional().orElseThrow();
        Long balance = fiatWallet.getBalance();
        Long newBalance = balance + depositAmount;
        fiatRepository.updateBalanceById(fiatAccountId, newBalance).block();
    }

    public void takeFiatAccount(Long fiatAccountId, Long takeAmount) {
        Mono<FiatWallet> fiatWallet = fiatRepository.findById(fiatAccountId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Fiat with id $fiatAccountId not found")));
        fiatWallet.flatMap(fiatWallet1 -> {
            Long balance = fiatWallet1.getBalance();
            long newBalance = balance - takeAmount;
            return fiatRepository.updateBalanceById(fiatAccountId, newBalance);
        }).block();
    }

    public Mono<Long> checkFiatBalanceFromWallet(Long fiatWalletId) {
        Mono<FiatWallet> fiatWallet = this.fiatRepository.findById(fiatWalletId).switchIfEmpty(Mono.error(new ResourceNotFoundException("Fiat Wallet with id $fiatAccountId not found")));
        return fiatWallet.map(FiatWallet::getBalance);
    }
}
