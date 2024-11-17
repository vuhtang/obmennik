package org.highload.controller;

import org.springframework.web.bind.annotation.RestController;


// ПРоверка ЭКСШЕПШЕН КАСТОМНЫЙ У НАС НЕТ ДЕНЕГ ЕСЛИ МОНЕТ НА АККАУНТЕ НАШЕМ НЕТ
// 1. берем рубли отнимаем у фиат валлеТ.
// 2 stock wallet наш - отнимаем у нас моенты
// 3. есть кошелек на аккаунте нашей крипты - кладем ему на кошелек
// Если все ок транзакция успешная - пишем запись в CoinBuyHistory


// Еще у аккаунта может быть эксченж ток монетами.

// БАНК УДАЛЯЕМ.
@RestController
public class StockController {
}
