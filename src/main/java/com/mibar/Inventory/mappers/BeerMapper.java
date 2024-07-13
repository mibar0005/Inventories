package com.mibar.Inventory.mappers;

//Create a Mapper interface
//Use the @Mapper bean

import com.mibar.Inventory.entities.Beer;
import com.mibar.Inventory.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDTO(Beer beer);




}
