package com.acmemini.payments;

import java.util.regex.Pattern;

/** A payment's IBAN must have the IBAN shape: two letters, two digits, then 11 to 30 letters or digits. */
public class IbanValidator {
    private static final Pattern IBAN = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$");

    public boolean isValid(String iban) {
        return iban != null && IBAN.matcher(iban.replace(" ", "")).matches();
    }
}
