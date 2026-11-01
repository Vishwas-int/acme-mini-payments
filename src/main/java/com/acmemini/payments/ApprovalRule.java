package com.acmemini.payments;

/** Payments above the threshold wait for a second approver before they are booked. */
public class ApprovalRule {
    public static final long THRESHOLD_EUR = 5_000;

    public boolean needsSecondApprover(long amountEur) {
        return amountEur > THRESHOLD_EUR;
    }
}
