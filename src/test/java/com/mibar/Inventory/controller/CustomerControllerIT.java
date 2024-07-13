package com.mibar.Inventory.controller;

import com.mibar.Inventory.entities.Customer;
import com.mibar.Inventory.model.CustomerDTO;
import com.mibar.Inventory.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testListCustomers() {
        List<CustomerDTO> dtos = customerController.listAllCustomers();
        Assertions.assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listAllCustomers();
        Assertions.assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO dto = customerController.getCustomerById(customer.getId());
        Assertions.assertThat(dto).isNotNull();
    }

    @Test
    void testBeerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    @Rollback
    @Transactional
    void testSaveCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("New Customer").build();
        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
//        Assertions.assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
//
//        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
//        UUID savedUUID = UUID.fromString(locationUUID[4]);
//        Customer customer = customerRepository.findById(savedUUID).get();
//        Assertions.assertThat(customer).isNotNull();
    }

}