package com.team.building.service;

import com.team.building.dtos.PlatDto;
import com.team.building.mappers.PlatMapper;
import com.team.building.model.Plat;
import com.team.building.repository.PlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class PlatService {
    PlatMapper platMapper = new PlatMapper();
    @Autowired
    PlatRepository platRepository;


    public Plat getPlatById(Long id) {
        return platRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Plat not found with id " + id));
    }
    public List<Plat> getAllPlat(){
        return (List<Plat>) platRepository.findAll();
    }
    public void createPlat(PlatDto response){
        Plat plat = new Plat();
        this.platMapper.mapFromRest(response , plat);
        this.platRepository.save(plat);
    }

    public Plat updatePlat(Long id, PlatDto response) {
        Optional<Plat> optionalPlat = platRepository.findById(id);
        if (optionalPlat.isPresent()) {
            Plat plat = optionalPlat.get();
            // Map only the fields except the ID
            this.platMapper.mapFromRest(response, plat);
            plat.setId(id); // Ensure the ID remains the same
            return this.platRepository.save(plat);
        } else {
            throw new NoSuchElementException("Plat not found with id: " + id);
        }
    }

    public void deletePlat(Long id) {
        if (platRepository.existsById(id)) {
            platRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Plat not found with id: " + id);
        }
    }
    public List<Plat> getAllPlats() {
        List<Plat> plats = new ArrayList<>();
        platRepository.findAll().forEach(plats::add);
        return plats;
    }

    public Map<Long, Plat> getAllPlatsAsMap() {
        List<Plat> plats = new ArrayList<>();
        platRepository.findAll().forEach(plats::add);
        return plats.stream().collect(Collectors.toMap(Plat::getId, plat -> plat));
    }
}