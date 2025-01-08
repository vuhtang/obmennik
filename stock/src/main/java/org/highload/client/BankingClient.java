package org.highload.client;

import org.apache.http.HttpStatus;
import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "banking-client")
public interface BankingClient {
    @PostMapping("banks/{id}/scripts/{scriptId}/status")
    HttpStatus changeFiatWalletBalance(@PathVariable("id") Long id, @PathVariable("scriptId") String scriptId, @RequestBody BuyCoinTransactionRequestBodyDTO buyCoinTransactionRequestBodyDTO);
}
