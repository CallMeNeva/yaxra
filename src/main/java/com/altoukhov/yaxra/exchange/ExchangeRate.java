// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.exchange;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

    @EmbeddedId
    private @NonNull Exchange exchange;

    @Column(name = "rate_value", nullable = false)
    private @NonNull BigDecimal value;

    public ExchangeRate(@NonNull Exchange exchange, @NonNull BigDecimal value) {
        this.exchange = Objects.requireNonNull(exchange);
        this.value = Objects.requireNonNull(value);
    }

    public ExchangeRate() {
        // JPA requirement, protected is OK per spec
    }

    public @NonNull Exchange getExchange() {
        return exchange;
    }

    public @NonNull BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ExchangeRate that
                && Objects.equals(this.exchange, that.exchange);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(exchange);
    }

    @Override
    public String toString() {
        return exchange + ", " + value;
    }
}
