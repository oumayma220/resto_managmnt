package com.team.building.repository;

import com.team.building.model.Panier;
import com.team.building.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PanierRepository extends JpaRepository<Panier, Long> {


}
