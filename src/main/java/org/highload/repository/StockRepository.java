package org.highload.repository;

import org.highload.model.StockAccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockAccountBalance, Long> {
}
