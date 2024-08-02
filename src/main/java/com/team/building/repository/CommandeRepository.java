package com.team.building.repository;

import com.team.building.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByTableRestaurantIdAndDate(Long tableId, LocalDate date);


}
