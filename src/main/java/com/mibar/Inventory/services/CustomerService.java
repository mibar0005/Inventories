package com.mibar.Inventory.services;

import com.mibar.Inventory.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<CustomerDTO> getCustomer(UUID uuid);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO addNewCustomer(CustomerDTO customer);

    void updateCustomer(UUID id, CustomerDTO customer);

    void deleteCustomer(UUID customerId);

    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
