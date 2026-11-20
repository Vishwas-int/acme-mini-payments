package com.acmemini.payments;

/** Accepts a payment instruction from the online banking channel and decides what happens to it. */
public class PaymentService {
    private final IbanValidator ibans = new IbanValidator();
    private final CurrencyCheck currencies = new CurrencyCheck();
    private final ApprovalRule approval = new ApprovalRule();
    private final LedgerWriter ledger;

    public PaymentService(LedgerWriter ledger) {
        this.ledger = ledger;
    }

    /** Returns the new status: REJECTED (bad IBAN or currency), PENDING_APPROVAL or BOOKED. */
    public String submit(String paymentId, String iban, long amount, String currency) {
        if (!ibans.isValid(iban) || !currencies.isSupported(currency)) {
            return "REJECTED";
        }
        if (approval.needsSecondApprover(amount)) {
            return "PENDING_APPROVAL";
        }
        ledger.book(paymentId, iban, amount, currency);
        return "BOOKED";
    }
}
