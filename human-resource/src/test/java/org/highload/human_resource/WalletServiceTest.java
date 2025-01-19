package org.highload.human_resource;

import org.highload.model.Account;
import org.highload.model.Wallet;
import org.highload.repository.WalletRepository;
import org.highload.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setPrivateKey("privateKey");
        Account account = new Account();
        account.setId(1L);
        wallet.setAccount(account);

        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet savedWallet = walletService.save(wallet);

        assertEquals(wallet, savedWallet);
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    public void testFindByAccountId() {
        Long accountId = 1L;
        Wallet wallet1 = new Wallet();
        wallet1.setId(1L);
        wallet1.setPrivateKey("privateKey1");
        Account account1 = new Account();
        account1.setId(accountId);
        wallet1.setAccount(account1);

        Wallet wallet2 = new Wallet();
        wallet2.setId(2L);
        wallet2.setPrivateKey("privateKey2");
        Account account2 = new Account();
        account2.setId(accountId);
        wallet2.setAccount(account2);

        List<Wallet> wallets = Arrays.asList(wallet1, wallet2);

        when(walletRepository.findAllByAccount_Id(accountId)).thenReturn(wallets);

        List<Wallet> foundWallets = walletService.findByAccountId(accountId);

        assertEquals(wallets, foundWallets);
        verify(walletRepository, times(1)).findAllByAccount_Id(accountId);
    }
}
