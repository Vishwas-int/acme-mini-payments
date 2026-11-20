# mini-payments 1.1.0

The ACME-MINI payment service. The online banking channel submits a payment; the service:

1. checks the IBAN (`IbanValidator`);
2. holds a payment above **10,000** (in the payment's currency) as `PENDING_APPROVAL` for a second approver (`ApprovalRule`);
3. otherwise books it: one row in `core.ledger_entry` (`LedgerWriter`) and the payment becomes `BOOKED`.

Payments live in the PostgreSQL database `minipay`, schema `core` (tables `payment` and `ledger_entry`).
The notifications service records one confirmation per booked payment in MongoDB `mininotify.notifications`.
Since 1.1.0 a payment is in EUR or GBP (`CurrencyCheck`); amounts are whole units of that currency.
