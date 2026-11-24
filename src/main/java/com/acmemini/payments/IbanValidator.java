package com.acmemini.payments;

import java.util.Set;
import java.util.regex.Pattern;

/** A payment's IBAN must have the IBAN shape and, since 1.1.1, come from a country ACME-MINI pays into. */
public class IbanValidator {
    private static final Pattern IBAN = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$");
    public static final Set<String> COUNTRIES = Set.of("DE", "NL", "FR", "GB", "IE");

    public boolean isValid(String iban) {
        if (iban == null) {
            return false;
        }
        String clean = iban.replace(" ", "");
        return IBAN.matcher(clean).matches() && COUNTRIES.contains(clean.substring(0, 2));
    }
}
