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

        fiatRepository.findById(fiatAccountId)
                .flatMap(fiatWallet -> {
                    fiatWallet.setBalance(fiatWallet.getBalance() - depositAmount);
                    return fiatRepository.save(fiatWallet);
                })
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Fiat with id $fiatAccountId not found")));


//        FiatWallet fiatWallet = fiatRepository.findById(fiatAccountId).;
//        Long balance = fiatWallet.getBalance();
//        fiatWallet.setBalance(balance + depositAmount);
//        fiatRepository.save(fiatWallet);
    }

    public void takeFiatAccount(Long fiatAccountId, Long takeAmount) {
         fiatRepository.findById(fiatAccountId)
                .flatMap(fiatWallet -> {
                    fiatWallet.setBalance(fiatWallet.getBalance() - takeAmount);
                    return fiatRepository.save(fiatWallet);
                })
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Fiat with id $fiatAccountId not found")));
//           TODO Как заапдейтить на моно
//        Long balance = fiatWallet.getBalance();
//        fiatWallet.setBalance(balance - takeAmount);
//        fiatRepository.save()
//        fiatRepository.save(fiatWallet);
    }

    public Mono<Long> checkFiatBalanceFromWallet(Long fiatWalletId){
        Mono<FiatWallet> fiatWallet = this.fiatRepository.findById(fiatWalletId);
        return fiatWallet.map(FiatWallet::getBalance);
    }
}
