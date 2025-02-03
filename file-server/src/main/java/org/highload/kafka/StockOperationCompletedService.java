package org.highload.kafka;

import lombok.RequiredArgsConstructor;
import org.highload.dto.CryptoWalletHasChange;
import org.highload.dto.StockOperationCompleted;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockOperationCompletedService {

    @KafkaListener(
            topics = "send-operation-completed",
            groupId = "file-server-consumer-group"
    )
    public void handleStockOperationCompleted(StockOperationCompleted message){
        System.out.println("Принято");
        System.out.println(message.toString());
    }
    
    @KafkaListener(
            topics = "send-crypto-wallet-has-change",
            groupId = "file-server-consumer-group"
    )
    public void handleCryptoWalletHasChange(CryptoWalletHasChange message){
        System.out.println("Принято CryptoWalletHasChange");
        System.out.println(message.toString());
    }
}