package org.highload.kafka;


import lombok.RequiredArgsConstructor;
import org.highload.dto.CryptoWalletHasChange;
import org.highload.dto.StockOperationCompleted;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void produceStockOperationCompleted(StockOperationCompleted stockOperationCompleted) {
        kafkaTemplate.send("send-operation-completed", stockOperationCompleted);
    }
    public void produceCryptoWalletHasChange(CryptoWalletHasChange cryptoWalletHasChange) {
        kafkaTemplate.send("send-crypto-wallet-has-change", cryptoWalletHasChange);
    }
}
