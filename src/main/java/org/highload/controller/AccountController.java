package org.highload.controller;

import org.highload.dto.AccountAccessesDTO;
import org.highload.dto.AccountInfoDTO;
import org.highload.dto.AccountShortInfoDTO;
import org.highload.dto.WalletDTO;
import org.highload.mappers.AccountMapper;
import org.highload.mappers.AccountWalletsMapper;
import org.highload.model.stock.Account;
import org.highload.model.stock.Wallet;
import org.highload.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;
    private AccountMapper accountMapper;
    private AccountWalletsMapper accountWalletsMapper;

    @GetMapping("/")
    public ResponseEntity<List<AccountShortInfoDTO>> getAllAccounts() {

        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accountMapper.mapListToShortDTO(accounts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountInfoDTO> getAccountById(@PathVariable Long id) {

        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(accountMapper.mapToDTO(account));
    }

    @GetMapping("/{id}/accesses")
    public ResponseEntity<AccountAccessesDTO> getAccountAccesses(@PathVariable Long id) {

        Set<String> accesses = accountService.getAccountAccesses(id);
        return ResponseEntity.ok(new AccountAccessesDTO(accesses));
    }

    @GetMapping("/{id}/wallets")
    public ResponseEntity<Set<WalletDTO>> getAccountWallets(@PathVariable Long id) {

        Set<Wallet> wallets = accountService.getAccountWallet(id);
        return ResponseEntity.ok(accountWalletsMapper.mapListToDTO(wallets));
    }

    @PostMapping("/{id}/wallets")
    public ResponseEntity<Void> addAccountWallet(@PathVariable Long id, @RequestBody String privateKey) {
        try {
            accountService.addAccountWallet(id, privateKey);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}