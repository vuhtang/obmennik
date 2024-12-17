package org.highload.controller;

import lombok.RequiredArgsConstructor;

import org.highload.client.StockClient;
import org.highload.dto.AccountShortInfoDTO;

import org.highload.mappers.AccountMapper;
import org.highload.mappers.AccountWalletsMapper;
import org.highload.model.Account;
import org.highload.service.AccountService;
import org.highload.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final AccountWalletsMapper accountWalletsMapper;
    private final UserService userService;
    private final StockClient stockClient;

    @GetMapping("/")
    public Mono<ResponseEntity<List<AccountShortInfoDTO>>> getAllAccounts(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size
    ) {
        Page<Account> accounts = accountService.getAllAccounts(page, size);
        List<AccountShortInfoDTO> dto = accountMapper.mapListToShortDTO(accounts.toList());
        if (dto.isEmpty())
            return Mono.just(ResponseEntity.noContent().build());
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(accounts.getTotalElements()));
        return Mono.just(new ResponseEntity<>(dto, headers, HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountInfoDTO> getAccountById(@PathVariable("id") Long id) {

        Account account = accountService.getAccountById(id);
        List<UserRole> userRoles = userService.getUserRolesById(account.getUser().getId());
        return ResponseEntity.ok(AccountMapper.mapToDTO(account, userRoles));
    }

    @GetMapping("/{id}/accesses")
    public ResponseEntity<AccountAccessesDTO> getAccountAccesses(@PathVariable("id") Long id) {

        List<String> accesses = accountService.getAccountAccesses(id);
        return ResponseEntity.ok(new AccountAccessesDTO(accesses));
    }

    @GetMapping("/{id}/wallets")
    public ResponseEntity<List<WalletDTO>> getAccountWallets(@PathVariable("id") Long id) {

        List<Wallet> wallets = accountService.getAccountWallets(id);

        return ResponseEntity.ok(accountService.getAccountWalletsDTO(wallets));
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
