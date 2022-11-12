// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.currency;

import org.springframework.lang.NonNull;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "currencies")
@Access(AccessType.FIELD)
public class Currency {

    private static final Pattern ALPHA_CODE_PATTERN = Pattern.compile("[A-Z]{3}");

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
        Objects.requireNonNull(alphabeticCode);
        Objects.requireNonNull(name);

        if (numericCode < 0 || numericCode > 999) {
            throw new IllegalArgumentException("Invalid ISO 4217 numeric code: " + numericCode);
        }

        Matcher alphaCodeMatcher = ALPHA_CODE_PATTERN.matcher(alphabeticCode);
        if (!alphaCodeMatcher.matches()) {
            throw new IllegalArgumentException("Invalid ISO 4217 alphabetic code: " + alphabeticCode);
        }

        return new Currency(numericCode, alphabeticCode, name);
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

    public @NonNull String getFormattedNumericCode() {
        throw new UnsupportedOperationException();
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
