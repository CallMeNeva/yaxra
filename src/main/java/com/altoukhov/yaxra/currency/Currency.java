// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.currency;

import org.springframework.lang.NonNull;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currencies")
@Access(AccessType.FIELD)
public class Currency {

    @Id
    @Column(name = "iso_4217_num_code", nullable = false)
    private short numericCode;

    @Column(name = "iso_4217_alpha_code", length = 3, nullable = false)
    private @NonNull String alphabeticCode;

    @Column(name = "name", length = 30, nullable = false)
    private @NonNull String name;

    public static @NonNull Currency createValidated(short numericCode,
                                                    @NonNull String alphabeticCode,
                                                    @NonNull String name) {
        throw new UnsupportedOperationException();
    }

    public static @NonNull Currency createUnvalidated(short numericCode,
                                                      @NonNull String alphabeticCode,
                                                      @NonNull String name) {
        return new Currency(numericCode, alphabeticCode, name);
    }

    private Currency(short numericCode, @NonNull String alphabeticCode, @NonNull String name) {
        this.numericCode = numericCode;
        this.alphabeticCode = alphabeticCode;
        this.name = name;
    }

    protected Currency() {
        // JPA requirement, protected is OK per spec
    }

    public short getNumericCode() {
        return numericCode;
    }

    public @NonNull String getAlphabeticCode() {
        return alphabeticCode;
    }

    public @NonNull String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Currency that && this.numericCode == that.numericCode;
    }

    @Override
    public int hashCode() {
        return Short.hashCode(numericCode);
    }

    @Override
    public String toString() {
        return name.isBlank() ? alphabeticCode : name;
    }
}
