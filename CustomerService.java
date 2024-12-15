package com.upwork.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.upwork.model.Customer;

public class CustomerService {
    private final Map<String, Customer> customers = new HashMap<>();

    public Customer getOrCreateCustomer(String id) {
        if (!customers.containsKey(id)) {
            Customer customer = Customer.builder().id(id).transactions(new ArrayList<>()).build();
            customers.put(id, customer);
        }
        return customers.get(id);
    }

    public Map<String, Customer> getAllCustomers() {
        return customers;
    }
}
