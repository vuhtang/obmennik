package org.highload.human_resource;

import org.highload.controller.AccountController;
import org.highload.dto.AccountAccessesDTO;
import org.highload.dto.AccountInfoDTO;
import org.highload.dto.AccountShortInfoDTO;
import org.highload.dto.WalletDTO;
import org.highload.mappers.AccountMapper;
import org.highload.mappers.AccountWalletsMapper;
import org.highload.model.Account;
import org.highload.model.User;
import org.highload.model.Wallet;
import org.highload.model.roles.UserRole;
import org.highload.service.AccountService;
import org.highload.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AccountWalletsMapper accountWalletsMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAccounts() {
        int page = 0;
        int size = 10;
        Account account1 = new Account();
        account1.setId(1L);
        Account account2 = new Account();
        account2.setId(2L);
        List<Account> accountsList = Arrays.asList(account1, account2);
        Page<Account> accountsPage = new PageImpl<>(accountsList);
        List<AccountShortInfoDTO> dtoList = Arrays.asList(
                AccountShortInfoDTO.builder().build(), AccountShortInfoDTO.builder().build()
        );

        when(accountService.getAllAccounts(page, size)).thenReturn(accountsPage);
        when(accountMapper.mapListToShortDTO(accountsList)).thenReturn(dtoList);

        Mono<ResponseEntity<List<AccountShortInfoDTO>>> responseMono = accountController.getAllAccounts(page, size);

        responseMono.subscribe(response -> {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dtoList, response.getBody());
            assertEquals("2", response.getHeaders().getFirst("X-Total-Count"));
        });

        verify(accountService, times(1)).getAllAccounts(page, size);
        verify(accountMapper, times(1)).mapListToShortDTO(accountsList);
    }

    @Test
    public void testGetAllAccountsNoContent() {
        int page = 0;
        int size = 10;
        Page<Account> accountsPage = new PageImpl<>(List.of());

        when(accountService.getAllAccounts(page, size)).thenReturn(accountsPage);

        Mono<ResponseEntity<List<AccountShortInfoDTO>>> responseMono = accountController.getAllAccounts(page, size);

        responseMono.subscribe(response -> {
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        });

        verify(accountService, times(1)).getAllAccounts(page, size);
    }

    @Test
    public void testGetAccountById() {
        Long id = 1L;
        Account account = new Account();
        User user = new User();
        user.setId(1L);
        user.setDateOfBirth(Date.valueOf("1996-01-01"));
        account.setUser(user);
        List<UserRole> userRoles = List.of(new UserRole());
        AccountInfoDTO accountInfoDTO = AccountInfoDTO.builder()
                .dob("1996-01-01").roles(new ArrayList<>(Collections.nCopies(1, null))).build();

        when(accountService.getAccountById(id)).thenReturn(account);
        when(userService.getUserRolesById(account.getUser().getId())).thenReturn(userRoles);
//        when(AccountMapper.mapToDTO(account, userRoles)).thenReturn(accountInfoDTO);

        ResponseEntity<AccountInfoDTO> response = accountController.getAccountById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountInfoDTO, response.getBody());

        verify(accountService, times(1)).getAccountById(id);
        verify(userService, times(1)).getUserRolesById(account.getUser().getId());
    }

    @Test
    public void testGetAccountAccesses() {
        Long id = 1L;
        List<String> accesses = List.of("access1", "access2");
        AccountAccessesDTO accountAccessesDTO = new AccountAccessesDTO(accesses);

        when(accountService.getAccountAccesses(id)).thenReturn(accesses);

        ResponseEntity<AccountAccessesDTO> response = accountController.getAccountAccesses(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountAccessesDTO, response.getBody());

        verify(accountService, times(1)).getAccountAccesses(id);
    }

    @Test
    public void testGetAccountWallets() {
        Long id = 1L;
        List<Wallet> wallets = List.of(new Wallet(), new Wallet());
        List<WalletDTO> walletDTOs = List.of(new WalletDTO(1L, List.of()), new WalletDTO(2L, List.of()));

        when(accountService.getAccountWallets(id)).thenReturn(wallets);
        when(accountService.getAccountWalletsDTO(wallets)).thenReturn(walletDTOs);

        ResponseEntity<List<WalletDTO>> response = accountController.getAccountWallets(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(walletDTOs, response.getBody());

        verify(accountService, times(1)).getAccountWallets(id);
        verify(accountService, times(1)).getAccountWalletsDTO(wallets);
    }

    @Test
    public void testAddAccountWalletSuccess() {
        Long id = 1L;
        String privateKey = "privateKey";

        doNothing().when(accountService).addAccountWallet(id, privateKey);

        ResponseEntity<Void> response = accountController.addAccountWallet(id, privateKey);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(accountService, times(1)).addAccountWallet(id, privateKey);
    }

    @Test
    public void testAddAccountWalletFailure() {
        Long id = 1L;
        String privateKey = "privateKey";

        doThrow(new RuntimeException()).when(accountService).addAccountWallet(id, privateKey);

        ResponseEntity<Void> response = accountController.addAccountWallet(id, privateKey);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(accountService, times(1)).addAccountWallet(id, privateKey);
    }
}
