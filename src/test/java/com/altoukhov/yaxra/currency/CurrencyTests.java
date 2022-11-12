// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.currency;

import com.altoukhov.yaxra.TestUtils;
import org.junit.jupiter.api.Test;

class CurrencyTests {

    @Test
    void equalsContract() {
        TestUtils.verifyEqualsForEntity(Currency.class);
    }
}
