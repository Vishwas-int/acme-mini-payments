package com.acmemini.payments;

/** Accepts a payment instruction from the online banking channel and decides what happens to it. */
public class PaymentService {
    private final IbanValidator ibans = new IbanValidator();
    private final ApprovalRule approval = new ApprovalRule();
    private final LedgerWriter ledger;

    public PaymentService(LedgerWriter ledger) {
        this.ledger = ledger;
    }

    /** Returns the new status: REJECTED (bad IBAN), PENDING_APPROVAL or BOOKED. */
    public String submit(String paymentId, String iban, long amountEur) {
        if (!ibans.isValid(iban)) {
            return "REJECTED";
        }
        if (approval.needsSecondApprover(amountEur)) {
            return "PENDING_APPROVAL";
        }
        ledger.book(paymentId, iban, amountEur);
        return "BOOKED";
    }
}
