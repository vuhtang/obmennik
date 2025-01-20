package org.highload.service;

import org.highload.dto.WalletDTO;
import org.highload.model.User;
import org.highload.model.roles.ControlAccess;
import org.highload.model.roles.UserRole;
import org.highload.model.stock.Account;
import org.highload.model.stock.Coin;
import org.highload.model.stock.CoinToWallet;
import org.highload.model.stock.Wallet;
import org.highload.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccessesService accessesService;

    @Mock
    private WalletService walletService;

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
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Account> accounts = new ArrayList<>();
        Page<Account> accountPage = new PageImpl<>(accounts, pageable, accounts.size());

        when(accountRepository.findAll(pageable)).thenReturn(accountPage);

        Page<Account> result = accountService.getAllAccounts(page, size);

        assertEquals(accountPage, result);
        verify(accountRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetAccountById() {
        Long id = 1L;
        Account account = new Account();
        account.setId(id);

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        Account result = accountService.getAccountById(id);

        assertEquals(account, result);
        verify(accountRepository, times(1)).findById(id);
    }

    @Test
    void testGetAccountAccesses() {
        Long id = 1L;
        Account account = new Account();
        account.setId(id);
        account.setUser(new User());
        UserRole userRole = new UserRole();
        userRole.setId(1L);
        ControlAccess controlAccess = new ControlAccess();
        controlAccess.setName("ACCESS_NAME");

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(userService.getUserRolesById(anyLong())).thenReturn(List.of(userRole));
        when(accessesService.getAllByRoleId(userRole.getId())).thenReturn(List.of(controlAccess));

        List<String> result = accountService.getAccountAccesses(id);

        verify(accountRepository, times(1)).findById(id);
    }

    @Test
    void testGetAccountWallets() {
        Long id = 1L;
        List<Wallet> wallets = new ArrayList<>();

        when(walletService.findByAccountId(id)).thenReturn(wallets);

        List<Wallet> result = accountService.getAccountWallets(id);

        assertEquals(wallets, result);
        verify(walletService, times(1)).findByAccountId(id);
    }

    @Test
    void testGetAccountWalletsDTO() {
        List<Wallet> wallets = new ArrayList<>();
        Wallet wallet = new Wallet();
        CoinToWallet coinToWallet = new CoinToWallet();
        Coin coin = new Coin();
        coin.setName("COIN_NAME");
        coinToWallet.setCoin(coin);
        coinToWallet.setAmount(100L);
        wallet.setCoins(Set.of(coinToWallet));
        wallets.add(wallet);

        List<WalletDTO> result = accountService.getAccountWalletsDTO(wallets);

        assertEquals(1, result.size());
        assertEquals("COIN_NAME", result.get(0).getCoins().get(0).getCoinType());
        assertEquals(100L, result.get(0).getCoins().get(0).getAmount());
    }

    @Test
    void testAddAccountWallet() {
        Long id = 1L;
        String privateKey = "PRIVATE_KEY";
        Account account = new Account();
        Wallet wallet = new Wallet();

        when(accountRepository.getReferenceById(id)).thenReturn(account);
        when(walletService.save(any(Wallet.class))).thenReturn(wallet);

        accountService.addAccountWallet(id, privateKey);

        verify(accountRepository, times(1)).getReferenceById(id);
        verify(walletService, times(1)).save(any(Wallet.class));
    }

    @Test
    void testCreateTestData() {
        Account account = new Account();
        User user = new User(1L, "Vova", "Vovkin", "vova2007@mail.ru", Date.valueOf(LocalDate.now()), Set.of());
        when(userService.createTestUser()).thenReturn(user);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.createTestData();

        verify(userService, times(1)).createTestUser();
        verify(accountRepository, times(1)).save(any(Account.class));
    }
}
