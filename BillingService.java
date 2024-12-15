package com.upwork.service;

import java.util.Map;
import java.util.stream.Collectors;

import com.upwork.model.Customer;
import com.upwork.model.Service;
import com.upwork.model.Transaction;

public class BillingService {
    public void processPurchase(Customer customer, com.upwork.model.Package pkg) {
        customer.addCredits(pkg.getCredits());
        customer.addTransaction(new Transaction("PURCHASE", pkg.getName(), true, customer.getCredits()));
    }

    public void processUsage(Customer customer, Service service, int times) {
        int totalCost = service.getCreditCost() * times;
        boolean success = customer.deductCredits(totalCost);
        customer.addTransaction(new Transaction("USAGE", service.getName() + " x " + times, success, customer.getCredits()));
    }

    public String generateTransactionSummary(Map<String, Customer> customerMap) {
        return customerMap.values().stream()
                .map(customer -> String.format("Customer: %s\nCredits: %d\nTransactions:\n%s\n", customer.getId(), customer.getCredits(),
                        customer.getTransactions().stream().map(Transaction::toString).collect(Collectors.joining("\n"))))
                .collect(Collectors.joining("\n"));
    }
}
