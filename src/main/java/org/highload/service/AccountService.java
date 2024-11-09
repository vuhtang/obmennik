package org.highload.service;

import org.highload.model.roles.ControlAccess;
import org.highload.model.stock.Account;
import org.highload.model.stock.Wallet;
import org.highload.repository.AccountRepository;
import org.highload.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private WalletRepository walletRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    public Set<String> getAccountAccesses(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        return account.getUser()
                .getRoles()
                .stream()
                .flatMap(userRole -> userRole.getAccesses().stream())
                .map(ControlAccess::getName)
                .collect(Collectors.toSet());
    }

    public Set<Wallet> getAccountWallet(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();

        return account.getWallets();
    }

    public void addAccountWallet(Long id, String privateKey) {
        Account account = accountRepository.findById(id).orElseThrow();

        Wallet entity = Wallet.builder()
                .privateKey(privateKey)
                .account(account)
                .build();
        walletRepository.save(entity);
    }
}

