package com.mibar.Inventory.repository;

import com.mibar.Inventory.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//Repositories are usually Interfaces. This should extend the JPArepository and pass in the beer entity and the UUID
public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
