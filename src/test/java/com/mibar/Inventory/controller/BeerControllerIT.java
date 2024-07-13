package com.mibar.Inventory.controller;

import com.mibar.Inventory.entities.Beer;
import com.mibar.Inventory.model.BeerDTO;
import com.mibar.Inventory.repository.BeerRepository;
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

//we will now create an integration test for the beer controller
@SpringBootTest //This is the full context to run tests
class BeerControllerIT {
    //We want to test the controller and its interaction with the JPA data layer
    //We are testing the controller methods as if we were the Spring Framework but we don't have web context
    //we are testing the interaction of controller with the service area

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers() {
        List<BeerDTO> dtos = beerController.listBeers();
        Assertions.assertThat(dtos.size()).isEqualTo(3);
    }

    //Create a test for when the list is empty
    //Add the transactional bean for this, if we do not use this, it will cause the test to fail
    //We want to use the rollback annotation to tell spring boot we want to roll back the changes we made
    //Since we are doing a deleteALl this is being persisted in the DB
    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        //Delete all beers from the beerRepository
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.listBeers();
        Assertions.assertThat(dtos.size()).isEqualTo(0);
    }

    //Test getBeerById()
    //We are throwing a NotFoundException that kicks in with SpringMvc logic
    //We want to write a test to make sure this is functioning properly along with the JPA service
    //This is the happy path
    @Test
    void testGetBeerById() {
        Beer beer = beerRepository.findAll().get(0);

        //With the controller we are going to expect a beer DTO
        BeerDTO dto = beerController.getBeerById(beer.getId());
        Assertions.assertThat(dto).isNotNull();
    }

    //Test for when a beer is not found by ID (unhappy path)
    @Test
    void testBeerByIdNotFound() {
        //For this we can pass a random UUID
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    //We want to test that the proper http status
    //WE want to test that the object gets persisted to the Database
    //get the id from the header and make a query
    //Since we are alternating the database we should use @Transactional and @Rollback as well
    //We are inserting a new object to the database
    @Transactional
    @Rollback
    @Test
    void saveNewBeerTest() {
        //Create a new BeerTO
        BeerDTO beerDTO = BeerDTO.builder().beerName("New Beer").build();
        //Create a ResponseEntity and make call to the beerController to create a new beer
        ResponseEntity responseEntity = beerController.handlePost(beerDTO);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        Assertions.assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[3]);

        Beer beer = beerRepository.findById(savedUUID).get();
        Assertions.assertThat(beer).isNotNull();
    }

}