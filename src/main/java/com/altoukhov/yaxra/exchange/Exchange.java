// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.exchange;

import com.altoukhov.yaxra.currency.Currency;
import org.springframework.lang.NonNull;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class Exchange implements Serializable {

    @Serial
    private static final long serialVersionUID = -1524445650210118461L;

    @ManyToOne
    @JoinColumn(name = "base_num_code", nullable = false)
    private @NonNull Currency baseCurrency;

    @ManyToOne
    @JoinColumn(name = "target_num_code", nullable = false)
    private @NonNull Currency targetCurrency;

    @Column(name = "date", nullable = false)
    private @NonNull LocalDate date;

    public static @NonNull Exchange createValidated(@NonNull Currency baseCurrency,
                                                    @NonNull Currency targetCurrency,
                                                    @NonNull LocalDate date) {
        Objects.requireNonNull(baseCurrency);
        Objects.requireNonNull(targetCurrency);
        Objects.requireNonNull(date);

        if (baseCurrency.equals(targetCurrency)) {
            throw new IllegalArgumentException("Base and target currencies must not be equal");
        }

        return new Exchange(baseCurrency, targetCurrency, date);
    }

    public static @NonNull Exchange createUnvalidated(@NonNull Currency baseCurrency,
                                                      @NonNull Currency targetCurrency,
                                                      @NonNull LocalDate date) {
        return new Exchange(baseCurrency, targetCurrency, date);
    }

    private Exchange(@NonNull Currency baseCurrency, @NonNull Currency targetCurrency, @NonNull LocalDate date) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.date = date;
    }

    protected Exchange() {
        // JPA requirement, protected is OK per spec
    }

    public @NonNull Currency getBaseCurrency() {
        return baseCurrency;
    }

    public @NonNull Currency getTargetCurrency() {
        return targetCurrency;
    }

    public @NonNull LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Exchange that
                && Objects.equals(this.baseCurrency, that.baseCurrency)
                && Objects.equals(this.targetCurrency, that.targetCurrency)
                && Objects.equals(this.date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrency, targetCurrency, date);
    }

    @Override
    public String toString() {
        return "%s --> %s at %s".formatted(
                baseCurrency.getAlphabeticCode(),
                targetCurrency.getAlphabeticCode(),
                date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }
}
