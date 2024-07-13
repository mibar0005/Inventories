package com.mibar.Inventory.services;

import com.mibar.Inventory.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;
   public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Jane Doe")
                .version(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

       CustomerDTO customer2 = CustomerDTO.builder()
               .id(UUID.randomUUID())
               .name("Mark Jones")
               .version(1)
               .createDate(LocalDateTime.now())
               .updateDate(LocalDateTime.now())
               .build();

       CustomerDTO customer3 = CustomerDTO.builder()
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
    public Optional<CustomerDTO> getCustomer(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
       //Create an arraylist and initialize this list with the values of customerMap and return as a new array
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public CustomerDTO addNewCustomer(CustomerDTO customer) {

       CustomerDTO saveCustomer = CustomerDTO.builder()
               .id(UUID.randomUUID())
               .name(customer.getName())
               .createDate(LocalDateTime.now())
               .updateDate(LocalDateTime.now())
               .version(customer.getVersion())
               .build();

       customerMap.put(saveCustomer.getId(), saveCustomer);
        return saveCustomer;
    }

    @Override
    public void updateCustomer(UUID id, CustomerDTO customer) {

       CustomerDTO existingCustomer = customerMap.get(id);
       existingCustomer.setName(customer.getName());

       customerMap.put(existingCustomer.getId(), existingCustomer);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(customerId);
        if (existing.getName() != null) {
            existing.setName(customer.getName());
        }
        customerMap.put(existing.getId(), existing);
    }

}
