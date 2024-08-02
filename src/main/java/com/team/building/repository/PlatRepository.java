package com.team.building.repository;

import com.team.building.model.Plat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface PlatRepository extends CrudRepository<Plat, Long> {
    Optional<Plat> findById(Long id);
}

