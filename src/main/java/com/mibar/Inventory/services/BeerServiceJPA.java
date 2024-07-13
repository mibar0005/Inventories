package com.mibar.Inventory.services;

import com.mibar.Inventory.mappers.BeerMapper;
import com.mibar.Inventory.model.BeerDTO;
import com.mibar.Inventory.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

//Create a stub will all the methods.
//Implement the BeerService Interface and create a BeerServiceJPA that will interact with the mappers and Repositories
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    //Bring in the BeerRepository
    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        //We are going to make a call to BeerRepository and call the findAll() method
        //We need to provide a list of the DTOs. We can use stream() and a map()
        //We want to have a beerMapper and we want to convert a beer to a beerDTO
        //This will convert from the beer entity object to the beerDTO. Then we can use
        //the collectors function to collect the data as a list
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        //We want to return an empty optional if it is not found, and we need to do a conversion
        //We also want to use the beerMapper and call the beerToBeerDTO and inside of this
        //call the beerRepository's findById method and pass in the id
        return Optional.ofNullable(beerMapper.beerToBeerDTO(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        //Call the beerMapper to traverse the beer to beerDTO
        //WE are taking the beer DTo and we are converting to a beer object. It passes that into the save
        // the save operation returns the saved object back which will have the UUID and version set that
        //we are emulating then we are taking that and passing it to the other converter it from the beer
        //entity to the beerDTO and then returning that.
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {

    }
}
