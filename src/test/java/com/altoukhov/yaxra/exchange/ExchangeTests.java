// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.exchange;

import com.altoukhov.yaxra.TestUtils;
import com.altoukhov.yaxra.currency.Currency;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assumptions.assumeThat;

class ExchangeTests {

    @Nested
    class CreateValidated {

        @Test
        void throwsIAEOnEqualCurrencies() {
            Currency base = Currency.createUnvalidated((short) 1, "FOO", "Foo Dollar");
            Currency target = Currency.createUnvalidated((short) 1, "FOO", "Foo Dollar");

            assumeThat(base.equals(target))
                    .isTrue();

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> Exchange.createValidated(base, target, LocalDate.EPOCH));
        }

        @Test
        void doesNotThrowOnNonEqualCurrencies() {
            Currency base = Currency.createUnvalidated((short) 1, "FOO", "Foo Dollar");
            Currency target = Currency.createUnvalidated((short) 2, "BAR", "Bar Dollar");

            assumeThat(base.equals(target))
                    .isFalse();

            assertThatNoException()
                    .isThrownBy(() -> Exchange.createValidated(base, target, LocalDate.EPOCH));
        }
    }

    @Test
    void equalsContract() {
        TestUtils.verifyEqualsFor(Exchange.class);
    }
}
