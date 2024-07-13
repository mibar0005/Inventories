package com.mibar.Inventory.bootstrap;

import com.mibar.Inventory.entities.Beer;
import com.mibar.Inventory.entities.Customer;
import com.mibar.Inventory.model.BeerStyle;
import com.mibar.Inventory.repository.BeerRepository;
import com.mibar.Inventory.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    //bring in the Beer and customer repository
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    //Bring in the data for the Beer Objects, however remove the version and the UUID since this will be
    //handled by Spring Boot
    private void loadBeerData() {
        //Check the count of the beer Repository
        if (beerRepository.count() == 0) {

            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("123456")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("The Can Can")
                    .beerStyle(BeerStyle.IPA)
                    .upc("951357")
                    .price(new BigDecimal("21.99"))
                    .quantityOnHand(52)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Riverside Rachets")
                    .beerStyle(BeerStyle.SOUR)
                    .upc("153624")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(65)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }

    private void loadCustomerData() {

        if(customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("Jane Doe")
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .name("Mark Jones")
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .name("Ray Sappo")
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
//
//        customerRepository.save(customer1);
//        customerRepository.save(customer2);
//        customerRepository.save(customer3);

            //Cleaner method
            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));

        }
    }
}
