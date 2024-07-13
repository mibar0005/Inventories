package com.mibar.Inventory.repository;

import com.mibar.Inventory.entities.Beer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest //Bean to use Data to Test JPA
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder().beerName("My Beer").build());

        Assertions.assertThat(savedBeer).isNotNull();
        Assertions.assertThat(savedBeer.getId()).isNotNull();

    }


}