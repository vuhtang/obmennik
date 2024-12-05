package org.highload.service;

import lombok.RequiredArgsConstructor;
import org.highload.dto.WalletDTO;
import org.highload.model.roles.ControlAccess;
import org.highload.model.roles.UserRole;
import org.highload.model.stock.Account;
import org.highload.model.stock.CoinToWallet;
import org.highload.model.stock.Wallet;
import org.highload.repository.AccountRepository;
import org.highload.repository.WalletRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccessesService accessesService;
    private final WalletService walletService;
    private final UserService userService;

    public Page<Account> getAllAccounts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findAll(pageable);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    public List<String> getAccountAccesses(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        List<UserRole> userRoles = userService.getUserRolesById(account.getUser().getId());

        List<String> accesses = new ArrayList<>();
        userRoles.forEach(role -> {
           List<ControlAccess> controlAccesses = accessesService.getAllByRoleId(role.getId());
           controlAccesses.forEach(access -> accesses.add(access.getName()));
        });
        return accesses;
    }

    public List<Wallet> getAccountWallets(Long id) {

        return walletService.findByAccountId(id);
    }

    public List<WalletDTO> getAccountWalletsDTO(List<Wallet> wallets) {
        List<WalletDTO> walletDTOS = new ArrayList<>();
        wallets.forEach(wallet -> {
            Set<CoinToWallet> coinToWallets = wallet.getCoins();
            List<WalletDTO.CoinDTO> coinDTOS = coinToWallets.stream().map(coinToWallet -> new WalletDTO.CoinDTO(
                    coinToWallet.getCoin().getName(),
                    coinToWallet.getAmount()
            )).toList();
            walletDTOS.add(new WalletDTO(wallet.getId(), coinDTOS));
        });

        return walletDTOS;
    }

    public void addAccountWallet(Long id, String privateKey) {
        Account account = accountRepository.getReferenceById(id);

        Wallet entity = new Wallet();
        entity.setAccount(account);
        entity.setPrivateKey(privateKey);
        walletService.save(entity);
    }


    public void createTestData(){
        Account account = new Account();
        account.setUser(userService.createTestUser());
        accountRepository.save(account);
    }

}

