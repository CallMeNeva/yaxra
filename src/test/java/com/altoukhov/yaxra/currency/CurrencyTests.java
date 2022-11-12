// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.currency;

import com.altoukhov.yaxra.TestUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CurrencyTests {

    @Nested
    class CreateValidated {

        @ParameterizedTest
        @MethodSource("provideBadArguments")
        void throwsIAEOnBadArguments(short numericCode, String alphabeticCode) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Currency.createValidated(numericCode, alphabeticCode, ""));
        }

        Stream<Arguments> provideBadArguments() {
            return Stream.of(
                    arguments((short) 978, "eur"),
                    arguments((short) 978, "EUr"),
                    arguments((short) 978, "EU"),
                    arguments((short) 978, "EURO"),
                    arguments((short) 978, "EU1"),
                    arguments((short) 978, "EU_"),
                    arguments((short) 0, ""),
                    arguments((short) 0, "   "),
                    arguments((short) 0, "\t"),
                    arguments((short) 2077, "BTC"),
                    arguments((short) -1, "XTS"),
                    arguments((short) 1000, "XTS")
            );
        }

        @ParameterizedTest
        @MethodSource("provideGoodArguments")
        void doesNotThrowOnGoodArguments(short numericCode, String alphabeticCode) {
            assertThatNoException()
                    .isThrownBy(() -> Currency.createValidated(numericCode, alphabeticCode, ""));
        }

        Stream<Arguments> provideGoodArguments() {
            // We could generate a set of all 3-letter [A-Z] permutations,
            // then Cartesian product it with [0, 999]. That'll surely be enough :)
            return Stream.of(
                    arguments((short) 978, "EUR"),
                    arguments((short) 840, "USD"),
                    arguments((short) 0, "FOO"),
                    arguments((short) 999, "BAR")
            );
        }
    }

    @Nested
    class NumericCodeFormatting {

        @ParameterizedTest
        @MethodSource("provideArguments")
        void formatsAsExpected(short numericCode, String expectedFormatting) {
            Currency currency = Currency.createUnvalidated(numericCode, "", "");

            assertThat(currency.getFormattedNumericCode())
                    .isEqualTo(expectedFormatting);
        }

        Stream<Arguments> provideArguments() {
            return Stream.of(
                    arguments((short) 0, "000"),
                    arguments((short) 5, "005"),
                    arguments((short) 50, "050"),
                    arguments((short) 500, "500")
            );
        }
    }

    @Test
    void equalsContract() {
        TestUtils.verifyEqualsForEntity(Currency.class);
    }
}
