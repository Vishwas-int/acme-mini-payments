package com.acmemini.payments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Books a payment: one row in core.ledger_entry, then the payment's status becomes BOOKED. */
public class LedgerWriter {
    private final Connection db;

    public LedgerWriter(Connection db) {
        this.db = db;
    }

    public void book(String paymentId, String iban, long amountEur) {
        try (PreparedStatement entry = db.prepareStatement(
                "INSERT INTO core.ledger_entry (payment_id, iban, amount, booked_at) VALUES (?, ?, ?, now())");
             PreparedStatement status = db.prepareStatement(
                "UPDATE core.payment SET status = 'BOOKED' WHERE id = ?")) {
            entry.setString(1, paymentId);
            entry.setString(2, iban);
            entry.setLong(3, amountEur);
            entry.executeUpdate();
            status.setString(1, paymentId);
            status.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("booking failed for " + paymentId, e);
        }
    }
}
