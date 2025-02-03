package org.highload.kafka;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.highload.datasource.MinioAdapter;
import org.highload.dto.CryptoWalletHasChange;
import org.highload.dto.StockOperationCompleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class StockOperationCompletedService {
    @Autowired
    private final MinioAdapter minioAdapter;

    @KafkaListener(
            topics = "send-operation-completed",
            groupId = "file-server-consumer-group"
    )
    public void handleStockOperationCompleted(StockOperationCompleted message) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        System.out.println(message.toString());
        String fileName = message.getUserWhoStartOperation().toString() + "-" + message.getDateOfOperation().toString() + ".json";
        minioAdapter.putObject(new ByteArrayInputStream(message.toString().getBytes()), fileName);
    }

    @KafkaListener(
            topics = "send-crypto-wallet-has-change",
            groupId = "file-server-consumer-group"
    )
    public void handleCryptoWalletHasChange(CryptoWalletHasChange message) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        System.out.println(message.toString());
        String fileName = "id" + message.getWalletId().toString() + "-" + message.getDateOfOperation().toString() + ".json";
        minioAdapter.putObject(new ByteArrayInputStream(message.toString().getBytes()), fileName);
    }
}