package org.highload.service;

import lombok.RequiredArgsConstructor;
//import org.highload.exceptions.WeHaveNoManeyException;
import org.highload.client.BankingClient;
import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.highload.dto.CryptoWalletHasChange;
import org.highload.dto.StockOperationCompleted;
import org.highload.exception.WeHaveNoManeyException;
import org.highload.kafka.KafkaProducer;
import org.highload.model.CoinToWallet;
import org.highload.model.StockAccountBalance;
import org.highload.repository.CoinToWalletRepository;
import org.highload.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class StockService {
    public static final Long STOCK_FIAT_ACCOUNT_ID = 2L;
    private final StockRepository stockRepository;
    private final BankingClient bankingClient;
    private final CoinToWalletRepository coinToWalletRepository;
    private final KafkaProducer kafkaProducer;

    @Transactional
    public void buyCoinByFiat(Long id, Long coinIdToBuy, Long amount, Long userFiatId) throws WeHaveNoManeyException {
        StockAccountBalance stockAccountBalance = stockRepository.findById(id).orElseThrow();

        Long currentAmount = stockAccountBalance.getAmount();
        if (stockAccountBalance.getCoin().getId().equals(coinIdToBuy) && currentAmount < amount) {
            throw new WeHaveNoManeyException("We have no money, sorry :((");
        }
        stockAccountBalance.setAmount(currentAmount - amount);
        stockRepository.save(stockAccountBalance);

        bankingClient.changeFiatWalletBalance(id, "depositFiatAccount", BuyCoinTransactionRequestBodyDTO.builder().userFiatId(userFiatId).amount(amount).build());
        kafkaProducer.produceStockOperationCompleted(
                new StockOperationCompleted(
                        amount,
                        userFiatId,
                        new Date()
                )
        );
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
        kafkaProducer.produceCryptoWalletHasChange(
                new CryptoWalletHasChange(
                        amount,
                        accountWallerId,
                        new Date()
                )
        );
        bankingClient.changeFiatWalletBalance(id, "takeFiatAccount", BuyCoinTransactionRequestBodyDTO.builder().userFiatId(accountWallerId).amount(amount).build());
    }


}
