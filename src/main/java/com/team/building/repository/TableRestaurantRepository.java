package com.team.building.repository;

import com.team.building.model.TableRestaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository


public interface TableRestaurantRepository extends JpaRepository<TableRestaurant, Long> {
    public Optional<TableRestaurant> findById(Long id);
    Optional<TableRestaurant> findByNumero(int numero);
}
