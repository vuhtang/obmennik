package org.highload.repository;

import org.highload.model.FiatWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiatRepository extends JpaRepository<FiatWallet, Long> {
}
