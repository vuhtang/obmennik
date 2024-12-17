package org.highload.repository;

import org.highload.model.FiatWallet;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiatRepository extends R2dbcRepository<FiatWallet, Long> {
}
