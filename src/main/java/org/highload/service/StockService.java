package org.highload.service;

import lombok.RequiredArgsConstructor;
import org.highload.exceptions.WeHaveNoManeyException;
import org.highload.model.stock.CoinToWallet;
import org.highload.model.stock.StockAccountBalance;
import org.highload.repository.CoinToWalletRepository;
import org.highload.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {
    public static final Long STOCK_FIAT_ACCOUNT_ID = 2L;
    private final StockRepository stockRepository;
    private final BankService bankService;

    private final CoinToWalletRepository coinToWalletRepository;

    @Transactional
    public void buyCoinByFiat(Long id, Long coinIdToBuy, Long amount, Long userFiatId) throws WeHaveNoManeyException{
        StockAccountBalance stockAccountBalance = stockRepository.findById(id).orElseThrow();

        Long currentAmount = stockAccountBalance.getAmount();
        if (stockAccountBalance.getCoin().getId().equals(coinIdToBuy) && currentAmount < amount) {
            throw new WeHaveNoManeyException("We have no money, sorry :((");
        }
        stockAccountBalance.setAmount(currentAmount - amount);
        stockRepository.save(stockAccountBalance);

        bankService.depositFiatAccount(userFiatId, amount);
    }

    @Transactional
    public void sellCoinByFiat(Long id, Long accountWallerId, Long amount) {

        CoinToWallet foundCryptoWalletByAccountWalletId = coinToWalletRepository.findAll()
                .stream()
                .filter(coinToWallet -> coinToWallet.getWallet().getId().equals(accountWallerId))
                .findFirst()
                .orElseThrow();

        foundCryptoWalletByAccountWalletId.setAmount(foundCryptoWalletByAccountWalletId.getAmount() + amount);

        coinToWalletRepository.save(foundCryptoWalletByAccountWalletId);

        bankService.takeFiatAccount(STOCK_FIAT_ACCOUNT_ID, amount);
    }


}
