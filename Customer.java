package com.upwork.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String id;
    private int credits;
    private List<Transaction> transactions;

    public void addCredits(int credits) {
        this.credits = this.credits + credits;
    }

    public void addTransaction(Transaction purchase) {
        this.getTransactions().add(purchase);
    }

    public boolean deductCredits(int credits) {
        if (this.getCredits() >= credits) {
            this.setCredits(this.getCredits() - credits);
            return true;
        }
        return false;
    }
}
