// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra.exchange;

import com.altoukhov.yaxra.TestUtils;
import org.junit.jupiter.api.Test;

class ExchangeRateTests {

    @Test
    void equalsContract() {
        TestUtils.verifyEqualsForEntity(ExchangeRate.class);
    }
}
