package com.acmemini.payments;

import java.math.BigInteger;
import java.util.regex.Pattern;

/** A payment's IBAN must have the IBAN shape (two letters, two digits, then 11 to 30 letters or digits) and, since
 *  1.2.0, a correct ISO 13616 mod-97 check sum, so a mistyped IBAN is rejected. */
public class IbanValidator {
    private static final Pattern IBAN = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$");
    private static final BigInteger NINETY_SEVEN = BigInteger.valueOf(97);

    public boolean isValid(String iban) {
        if (iban == null) {
            return false;
        }
        String compact = iban.replace(" ", "");
        return IBAN.matcher(compact).matches() && checkSumOk(compact);
    }

    private boolean checkSumOk(String iban) {
        String moved = iban.substring(4) + iban.substring(0, 4);
        StringBuilder digits = new StringBuilder();
        for (char c : moved.toCharArray()) {
            digits.append(Character.isLetter(c) ? String.valueOf(c - 'A' + 10) : String.valueOf(c));
        }
        return new BigInteger(digits.toString()).mod(NINETY_SEVEN).intValue() == 1;
    }
}
