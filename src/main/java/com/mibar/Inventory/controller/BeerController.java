package com.mibar.Inventory.controller;

import com.mibar.Inventory.model.Beer;
import com.mibar.Inventory.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor  //Creates the constructor for us at runtime (look at target folder)
@RestController  //Sets up to return Response body (returns JSON and not HTML)
public class BeerController {

    //Create a constant variable and store both Types of URI's in order to follow DRY principle

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }


    @GetMapping(BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get Beer by Id - in Controller - 1234");
        return beerService.getBeerById(beerId);
    }

    //Return a ResponseEntity since we are creating a new object
//    @RequestMapping(method = RequestMethod.POST)
//    @RequestBody --> handles the post request body (otherwise the values you pass will appear as null)
    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);
        //Return a ResponseEntity of http created (201)

        //Create an HTTP header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/beer/" + savedBeer.getId().toString());

        //We can the http headers as a parameter into the http constructors
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    /**
     * PUT is a combination of GET by ID and POST
     * This will return a ResponseEntity of NoContent
     * This will take in a UUID and a Beer Object
     * It will also use the @RequestBody and @PathVariable annotations
     * Tip: @PathVariable value should be a regular string
     * Make sure that the beerId for the endpoint, PathVariable, parameters and arguments matches!!!!
     *
     **/
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {

        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    /**
     * DELETE
     */

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID beerId) {

        beerService.deleteById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * PATCH
     */
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {

        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
