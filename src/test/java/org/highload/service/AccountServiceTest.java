package org.highload.service;

import org.highload.dto.WalletDTO;
import org.highload.model.User;
import org.highload.model.roles.ControlAccess;
import org.highload.model.roles.UserRole;
import org.highload.model.Account;
import org.highload.model.Coin;
import org.highload.model.CoinToWallet;
import org.highload.model.Wallet;
import org.highload.repository.AccessesRepository;
import org.highload.repository.AccountRepository;
import org.highload.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccessesRepository accessesRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAccounts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Account> accounts = List.of(new Account(), new Account());
        Page<Account> accountPage = new PageImpl<>(accounts);

        when(accountRepository.findAll(pageable)).thenReturn(accountPage);

        Page<Account> result = accountService.getAllAccounts(0, 10);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(accountRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetAccountById() {
        Account account = new Account();
        account.setId(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.getAccountById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAccountAccesses() {
        Account account = new Account();
        account.setId(1L);
        UserRole role = new UserRole();
        role.setId(1L);
        ControlAccess access = new ControlAccess();
        access.setName("READ");
        User user = new User();
        user.setId(1L);
        account.setUser(user);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(userService.getUserRolesById(anyLong())).thenReturn(List.of(role));
        when(accessesRepository.findAllByUserRoleId(1L)).thenReturn(List.of(access));

        List<String> result = accountService.getAccountAccesses(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("READ", result.get(0));
        verify(accessesRepository, times(1)).findAllByUserRoleId(1L);
    }

    @Test
    void testGetAccountWallets() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);

        when(walletRepository.findAllByAccount_Id(1L)).thenReturn(List.of(wallet));

        List<Wallet> result = accountService.getAccountWallets(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(walletRepository, times(1)).findAllByAccount_Id(1L);
    }

    @Test
    void testGetAccountWalletsDTO() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);

        CoinToWallet coinToWallet = new CoinToWallet();
        coinToWallet.setAmount(10L);
        coinToWallet.setCoin(new Coin());
        coinToWallet.getCoin().setName("BTC");

        wallet.setCoins(Set.of(coinToWallet));

        List<WalletDTO> result = accountService.getAccountWalletsDTO(List.of(wallet));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getCoins().get(0).getCoinType());
        assertEquals(10, result.get(0).getCoins().get(0).getAmount());
    }

    @Test
    void testAddAccountWallet() {
        Account account = new Account();
        account.setId(1L);
        String privateKey = "private_key";

        when(accountRepository.getReferenceById(1L)).thenReturn(account);

        accountService.addAccountWallet(1L, privateKey);

        verify(walletRepository, times(1)).save(any(Wallet.class));
    }
}
