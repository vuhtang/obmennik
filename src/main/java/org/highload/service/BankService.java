package org.highload.service;

import lombok.RequiredArgsConstructor;
import org.highload.model.stock.FiatWallet;
import org.highload.repository.FiatRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {
    private final FiatRepository fiatRepository;


    public void depositFiatAccount(Long fiatAccountId, Long depositAmount) {
        FiatWallet fiatWallet = fiatRepository.findById(fiatAccountId).orElseThrow();
        Long balance = fiatWallet.getBalance();
        fiatWallet.setBalance(balance + depositAmount);
        fiatRepository.save(fiatWallet);
    }

    public void takeFiatAccount(Long fiatAccountId, Long takeAmount) {
        FiatWallet fiatWallet = fiatRepository.findById(fiatAccountId).orElseThrow();
        Long balance = fiatWallet.getBalance();
        fiatWallet.setBalance(balance - takeAmount);
        fiatRepository.save(fiatWallet);
    }
}
