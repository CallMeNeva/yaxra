// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.exchange;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Exchange> {

    @Query("SELECT e from ExchangeRate e WHERE e.exchange.baseCurrency.numericCode = ?1 AND e.exchange.date = ?2")
    List<ExchangeRate> findByBaseAndDate(short baseNumericCode, LocalDate date);
}
