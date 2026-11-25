package com.acmemini.payments;

import java.util.HashMap;
import java.util.Map;

/** Since 1.2.0 one payer may send at most 50,000 per day (all currencies added up as whole units). */
public class DailyLimit {
    public static final long PER_PAYER_PER_DAY = 50_000;

    private final Map<String, Long> sentToday = new HashMap<>();

    public boolean allows(String payerIban, long amount) {
        return sentToday.getOrDefault(payerIban, 0L) + amount <= PER_PAYER_PER_DAY;
    }

    public void record(String payerIban, long amount) {
        sentToday.merge(payerIban, amount, Long::sum);
    }
}
