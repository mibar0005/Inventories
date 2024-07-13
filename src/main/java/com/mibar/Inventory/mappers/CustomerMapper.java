package com.mibar.Inventory.mappers;

import com.mibar.Inventory.entities.Customer;
import com.mibar.Inventory.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
