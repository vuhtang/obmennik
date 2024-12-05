package org.highload.service;

import lombok.RequiredArgsConstructor;
import org.highload.model.stock.Wallet;
import org.highload.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet save(Wallet wallet){
        return walletRepository.save(wallet);
    }

    public List<Wallet> findByAccountId(Long id) {
        return walletRepository.findAllByAccount_Id(id);
    }
}
