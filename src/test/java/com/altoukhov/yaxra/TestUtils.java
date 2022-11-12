// SPDX-License-Identifier: Unlicense

package com.altoukhov.yaxra;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public final class TestUtils {

    private TestUtils() {}

    public static void verifyEqualsForEntity(Class<?> entityClass) {
        EqualsVerifier.forClass(entityClass)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }
}
