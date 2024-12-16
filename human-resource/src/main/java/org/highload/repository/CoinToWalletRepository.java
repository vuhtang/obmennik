package org.highload.repository;

import org.highload.model.CoinToWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinToWalletRepository extends JpaRepository<CoinToWallet, Long> {
}
