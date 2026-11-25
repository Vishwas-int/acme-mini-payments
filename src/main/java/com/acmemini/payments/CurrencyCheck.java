package com.acmemini.payments;

import java.util.Set;

/** Since 1.2.0 a payment may be made in EUR, GBP or CHF; any other currency is rejected. */
public class CurrencyCheck {
    public static final Set<String> SUPPORTED = Set.of("EUR", "GBP", "CHF");

    public boolean isSupported(String currency) {
        return currency != null && SUPPORTED.contains(currency);
    }
}
