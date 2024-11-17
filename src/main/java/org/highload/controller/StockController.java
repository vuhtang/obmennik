package org.highload.controller;

import lombok.RequiredArgsConstructor;
import org.highload.dto.BuyCoinTransactionRequestBodyDTO;
import org.highload.service.StockService;
import org.springframework.web.bind.annotation.*;


// ПРоверка ЭКСШЕПШЕН КАСТОМНЫЙ У НАС НЕТ ДЕНЕГ ЕСЛИ МОНЕТ НА АККАУНТЕ НАШЕМ НЕТ
// 1. берем рубли отнимаем у фиат валлеТ.
// 2 stock wallet наш - отнимаем у нас моенты
// 3. есть кошелек на аккаунте нашей крипты - кладем ему на кошелек
// Если все ок транзакция успешная - пишем запись в CoinBuyHistory


// Еще у аккаунта может быть эксченж ток монетами.

// БАНК УДАЛЯЕМ.
@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
//    TODO name of method
    @PostMapping("/{id}/buyCoinByFiat")
    public void buyCoinByFiat(@PathVariable("id") Long id, @RequestBody BuyCoinTransactionRequestBodyDTO buyCoinTransactionRequestBodyDTO){
        stockService.buyCoinByFiat(
                id,
                buyCoinTransactionRequestBodyDTO.getCoinIdToBuy(),
                buyCoinTransactionRequestBodyDTO.getAmount(),
                buyCoinTransactionRequestBodyDTO.getUserFiatId()
        );
    }

    @PostMapping("/{id}/sellCoinByFiat")
    public void sellCoinByFiat(@PathVariable("id") Long id, @RequestBody BuyCoinTransactionRequestBodyDTO buyCoinTransactionRequestBodyDTO){
        stockService.sellCoinByFiat(
                id,
                buyCoinTransactionRequestBodyDTO.getCoinIdToBuy(),
                buyCoinTransactionRequestBodyDTO.getAmount()
        );
    }
}
