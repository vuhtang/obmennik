package org.highload.controller;

import lombok.RequiredArgsConstructor;
import org.highload.dto.AccountAccessesDTO;
import org.highload.dto.AccountInfoDTO;
import org.highload.dto.AccountShortInfoDTO;
import org.highload.dto.WalletDTO;
import org.highload.mappers.AccountMapper;
import org.highload.mappers.AccountWalletsMapper;
import org.highload.model.stock.Account;
import org.highload.model.stock.Wallet;
import org.highload.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final AccountWalletsMapper accountWalletsMapper;

    @GetMapping("/")
    public ResponseEntity<List<AccountShortInfoDTO>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Max(50) int size
    ) {

        Page<Account> accounts = accountService.getAllAccounts(page, size);
        ResponseEntity<List<AccountShortInfoDTO>> responseEntity = ResponseEntity.ok(accountMapper.mapListToShortDTO(accounts.toList()));
        responseEntity.getHeaders().add("X-Total-Count", String.valueOf(accounts.getTotalElements()));
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountInfoDTO> getAccountById(@PathVariable("id") Long id) {

        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(accountMapper.mapToDTO(account));
    }

    @GetMapping("/{id}/accesses")
    public ResponseEntity<AccountAccessesDTO> getAccountAccesses(@PathVariable("id") Long id) {

        Set<String> accesses = accountService.getAccountAccesses(id);
        return ResponseEntity.ok(new AccountAccessesDTO(accesses));
    }

    @GetMapping("/{id}/wallets")
    public ResponseEntity<Set<WalletDTO>> getAccountWallets(@PathVariable("id") Long id) {

        Set<Wallet> wallets = accountService.getAccountWallet(id);
        return ResponseEntity.ok(accountWalletsMapper.mapListToDTO(wallets));
    }

    @PostMapping("/{id}/wallets")
    public ResponseEntity<Void> addAccountWallet(@PathVariable("id") Long id, @RequestBody String privateKey) {
        try {
            accountService.addAccountWallet(id, privateKey);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
