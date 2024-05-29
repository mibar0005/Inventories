package com.mibar.Inventory.services;

import com.mibar.Inventory.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerMap;
   public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("Jane Doe")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

       Customer customer2 = Customer.builder()
               .id(UUID.randomUUID())
               .name("Mark Jones")
               .version(1)
               .createDate(LocalDateTime.now())
               .updateDate(LocalDateTime.now())
               .build();

       Customer customer3 = Customer.builder()
               .id(UUID.randomUUID())
               .name("Ray Sappo")
               .version(1)
               .createDate(LocalDateTime.now())
               .updateDate(LocalDateTime.now())
               .build();

       customerMap.put(customer1.getId(), customer1);
       customerMap.put(customer2.getId(), customer2);
       customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public Customer getCustomer(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
       //Create an arraylist and initialize this list with the values of customerMap and return as a new array
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer addNewCustomer(Customer customer) {

       Customer saveCustomer = Customer.builder()
               .id(UUID.randomUUID())
               .name(customer.getName())
               .createDate(LocalDateTime.now())
               .updateDate(LocalDateTime.now())
               .version(customer.getVersion())
               .build();

       customerMap.put(saveCustomer.getId(), saveCustomer);
        return saveCustomer;
    }
}
