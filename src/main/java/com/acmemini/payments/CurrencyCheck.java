package com.acmemini.payments;

import java.util.Set;

/** Since 1.1.0 a payment may be made in EUR or GBP; any other currency is rejected. */
public class CurrencyCheck {
    public static final Set<String> SUPPORTED = Set.of("EUR", "GBP");

    public boolean isSupported(String currency) {
        return currency != null && SUPPORTED.contains(currency);
    }
}
