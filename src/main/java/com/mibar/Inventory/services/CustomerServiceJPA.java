package com.mibar.Inventory.services;

import com.mibar.Inventory.mappers.CustomerMapper;
import com.mibar.Inventory.model.CustomerDTO;
import com.mibar.Inventory.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;
    @Override
    public Optional<CustomerDTO> getCustomer(UUID uuid) {
        return Optional.ofNullable(customerMapper.customerToCustomerDTO(customerRepository.findById(uuid).orElse(null)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO addNewCustomer(CustomerDTO customer) {

//        beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
        return customerMapper.customerToCustomerDTO(customerRepository.save(customerMapper.customerDtoToCustomer(customer)));
    }

    @Override
    public void updateCustomer(UUID id, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomer(UUID customerId) {

    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
}
