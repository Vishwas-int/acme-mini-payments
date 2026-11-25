# mini-payments 1.2.0

The ACME-MINI payment service. The online banking channel submits a payment; the service:

1. checks the IBAN: its shape and, since 1.2.0, its mod-97 check sum (`IbanValidator`);
2. checks the currency: EUR, GBP or, since 1.2.0, CHF (`CurrencyCheck`);
3. since 1.2.0, rejects a payment that takes the payer over **50,000 per day** as `REJECTED_LIMIT` (`DailyLimit`);
4. holds a payment above **10,000** (in the payment's currency) as `PENDING_APPROVAL` for a second approver (`ApprovalRule`);
5. otherwise books it: one row in `core.ledger_entry` (`LedgerWriter`) and the payment becomes `BOOKED`.

Payments live in the PostgreSQL database `minipay`, schema `core` (tables `payment` and `ledger_entry`).
The notifications service records one confirmation per booked payment in MongoDB `mininotify.notifications`.
Amounts are whole units of the payment's currency. Made-up test data for an MSOCK refresh demo.
