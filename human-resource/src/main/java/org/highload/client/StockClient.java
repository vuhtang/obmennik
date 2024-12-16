package org.highload.client;

import org.highload.dto.AccountShortInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "stock-client")
public interface StockClient {
    @GetMapping("/stock/testFeign")
    AccountShortInfoDTO test();
}
