package org.highload.human_resource.unit;

import org.highload.dto.WalletDTO;
import org.highload.model.*;
import org.highload.model.roles.ControlAccess;
import org.highload.model.roles.UserRole;
import org.highload.repository.AccountRepository;
import org.highload.service.AccessesService;
import org.highload.service.AccountService;
import org.highload.service.UserService;
import org.highload.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

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

    private Account account;
    private UserRole userRole;
    private ControlAccess controlAccess;
    private Wallet wallet;
    private CoinToWallet coinToWallet;
    private Coin coin;
    private User user;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(1L);

        user = new User();
        user.setId(1L);

        account.setUser(user);

        userRole = new UserRole();
        userRole.setId(1L);
        userRole.setName("ROLE_USER");

        controlAccess = new ControlAccess();
        controlAccess.setId(1L);
        controlAccess.setName("ACCESS_CONTROL");

        wallet = new Wallet();
        wallet.setId(1L);
        wallet.setAccount(account);
        wallet.setPrivateKey("privateKey");

        coin = new Coin();
        coin.setId(1L);
        coin.setName("CoinName");

        coinToWallet = new CoinToWallet();
        coinToWallet.setId(1);
        coinToWallet.setWallet(wallet);
        coinToWallet.setCoin(coin);
        coinToWallet.setAmount(100L);
    }

    @Test
    public void testGetAllAccounts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Account> accounts = Collections.singletonList(account);
        Page<Account> accountPage = new PageImpl<>(accounts, pageable, accounts.size());

        when(accountRepository.findAll(pageable)).thenReturn(accountPage);

        Page<Account> result = accountService.getAllAccounts(0, 10);
        assertEquals(1, result.getTotalElements());
        assertEquals(account, result.getContent().get(0));
    }

    @Test
    public void testGetAccountById() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.getAccountById(1L);
        assertEquals(account, result);
    }

    @Test
    public void testGetAccountById_NotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> accountService.getAccountById(1L));
    }

    @Test
    public void testGetAccountAccesses() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(userService.getUserRolesById(anyLong())).thenReturn(Collections.singletonList(userRole));
        when(accessesService.getAllByRoleId(1L)).thenReturn(Collections.singletonList(controlAccess));

        List<String> result = accountService.getAccountAccesses(1L);
        assertEquals(1, result.size());
        assertEquals("ACCESS_CONTROL", result.get(0));
    }

    @Test
    public void testGetAccountWallets() {
        when(walletService.findByAccountId(1L)).thenReturn(Collections.singletonList(wallet));

        List<Wallet> result = accountService.getAccountWallets(1L);
        assertEquals(1, result.size());
        assertEquals(wallet, result.get(0));
    }

    @Test
    public void testGetAccountWalletsDTO() {
        List<Wallet> wallets = Collections.singletonList(wallet);
        Set<CoinToWallet> coinToWallets = new HashSet<>();
        coinToWallets.add(coinToWallet);
        wallet.setCoins(coinToWallets);

        List<WalletDTO> result = accountService.getAccountWalletsDTO(wallets);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1, result.get(0).getCoins().size());
        assertEquals("CoinName", result.get(0).getCoins().get(0).getCoinType());
        assertEquals(100L, result.get(0).getCoins().get(0).getAmount());
    }

    @Test
    public void testAddAccountWallet() {
        when(accountRepository.getReferenceById(1L)).thenReturn(account);

        accountService.addAccountWallet(1L, "privateKey");

        verify(walletService, times(1)).save(any(Wallet.class));
    }

    @Test
    public void testCreateTestData() {
        when(userService.createTestUser()).thenReturn(new User());

        accountService.createTestData();

        verify(accountRepository, times(1)).save(any(Account.class));
    }
}
