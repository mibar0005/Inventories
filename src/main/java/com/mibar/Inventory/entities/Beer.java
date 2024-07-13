package com.mibar.Inventory.entities;

import com.mibar.Inventory.model.BeerStyle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

//This will be exact copies the Beer POJO (or BeerDTO at this point)
//Use the same beans (Data and Builder) as you did for the POJOS
//You will need to add the @Entity bean in order to tell Spring this will be an entity (You will need
//an @Id and a @Version bean as well
//We will also need to add an AllArgsConstructor and a NoArgsConstructor else it will throw an error
//It is NOT recommended to use @Data for JPA entities memory issues (Use @Getters and @Setters instead)
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

    //Set up the id field be handled by hibernate
    @Id
    //This is how JPA is going to generate the value for ID
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    //Use the column annotaton to state a column and give it a definition . This gives hints to SQl
    //When it is creating the Database
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
