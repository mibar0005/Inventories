package com.mibar.Inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mibar.Inventory.model.BeerDTO;
import com.mibar.Inventory.services.BeerService;
import com.mibar.Inventory.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//We are using @WebMvcTest rather than @SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    //We are doing to be using the MockMvc context to mock crud calls
    @Autowired
    MockMvc mockMvc;


    //ObjectMapper --> helps us serialize and deserialize data from JSON. it can take POJOs to JSON and vice versa
    @Autowired
    ObjectMapper objectMapper;

    @MockBean   //tells mockito to provide a mock of this into the Spring Context
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        //We are creating a new beer object
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        //mocking a patch call to our service with the new object being passed via object mapper
        mockMvc.perform(patch( BeerController.BEER_PATH_ID, beer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());
        //verify that the beerService patchBeerById method was called.
        //We have two argument captures to listen for the values being passed in
        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        //Assert that the id is eual to the one from the uuid arg capture
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        //assert that beermap's beerName is equal to the same capture from beerArguemnt captured
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //Argument Capture --> captures the property that is being sent
        //This will sit on the mock and wait for the value to be passed and make sure the value
        //was parsed correctly and sent that.
        verify(beerService).deleteById(uuidArgumentCaptor.capture());
        //We can run assertions.
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        //We are using put method without URL + beerId.
        //We also use our objectMapper to pass in the beer object we are expecting a noContent response
        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        //Verify --> used to verify that an action occurred
        verify(beerService).updateBeerById(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void testCreateNewBeer() throws Exception {
        //Creating a new beer object and setting version and id to null
        BeerDTO beer = beerServiceImpl.listBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);

        //Given beerService to save a new beer (beer oject) will return a beer from beer service impl
        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

        //Use mockMvc to perform a post to the url, inside contentType, we need to add an additional field
        //.content() where we use our object mapper to write our beer object.
        //We are excepting the status to be of isCreated() rather than isOK.
        //We are also checking to make sure a header named Location exists.
        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }



    @Test
    void getBeerById() throws Exception {
        BeerDTO testBeer = beerServiceImpl.listBeers().get(0);

        //Here, what we are telling mockito is:
        //given beerService.getBeerById with any UUID class passed in, then return the test beer object
        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        //We are using the mock framework to perform a get function
        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    //Create a test for when a beer is not found
    @Test
    void getBeerByIdNotFound() throws Exception {

        //Set up mockito to return that exception
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        //Mock the call to mvc with a status of isNotFound
        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}