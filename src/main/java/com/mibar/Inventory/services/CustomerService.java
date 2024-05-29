package com.mibar.Inventory.services;

import com.mibar.Inventory.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer getCustomer(UUID uuid);

    List<Customer> getAllCustomers();

    Customer addNewCustomer(Customer customer);
}
