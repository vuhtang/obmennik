package org.highload.repository;

import org.highload.model.FiatWallet;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FiatRepository extends R2dbcRepository<FiatWallet, Long> {
    @Modifying
    @Query("UPDATE fiat_wallet SET balance = :balance WHERE id = :id")
    Mono<Long> updateBalanceById(@Param("id")Long id, @Param("balanceAmount")Long balance);
}
