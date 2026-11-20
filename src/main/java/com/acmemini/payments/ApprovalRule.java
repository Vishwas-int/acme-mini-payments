package com.acmemini.payments;

/** Payments above the threshold wait for a second approver before they are booked (raised in 1.1.0). */
public class ApprovalRule {
    public static final long THRESHOLD = 10_000;

    public boolean needsSecondApprover(long amount) {
        return amount > THRESHOLD;
    }
}
