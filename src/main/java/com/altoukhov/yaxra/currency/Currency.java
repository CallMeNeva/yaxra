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
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return name.isBlank() ? alphabeticCode : name;
    }
}
