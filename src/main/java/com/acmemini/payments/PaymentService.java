package com.acmemini.payments;

/** Accepts a payment instruction from the online banking channel and decides what happens to it. */
public class PaymentService {
    private final IbanValidator ibans = new IbanValidator();
    private final CurrencyCheck currencies = new CurrencyCheck();
    private final ApprovalRule approval = new ApprovalRule();
    private final DailyLimit dailyLimit = new DailyLimit();
    private final LedgerWriter ledger;

    public PaymentService(LedgerWriter ledger) {
        this.ledger = ledger;
    }

    /** Returns the new status: REJECTED (bad IBAN or currency), REJECTED_LIMIT (over the payer's daily limit),
     *  PENDING_APPROVAL or BOOKED. */
    public String submit(String paymentId, String iban, long amount, String currency) {
        if (!ibans.isValid(iban) || !currencies.isSupported(currency)) {
            return "REJECTED";
        }
        if (!dailyLimit.allows(iban, amount)) {
            return "REJECTED_LIMIT";
        }
        dailyLimit.record(iban, amount);
        if (approval.needsSecondApprover(amount)) {
            return "PENDING_APPROVAL";
        }
        ledger.book(paymentId, iban, amount, currency);
        return "BOOKED";
    }
}
