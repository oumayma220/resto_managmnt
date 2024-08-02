package com.team.building.mappers;

import com.team.building.dtos.CommandeDTO;
import com.team.building.model.Commande;
import com.team.building.model.Plat;
import com.team.building.model.TableRestaurant;
import com.team.building.repository.PlatRepository;
import com.team.building.repository.TableRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Component

public class CommandeMapper {

    private final TableRestaurantRepository tableRestaurantRepository;
    private final PlatRepository platRepository;

    @Autowired
    public CommandeMapper(TableRestaurantRepository tableRestaurantRepository, PlatRepository platRepository) {
        this.tableRestaurantRepository = tableRestaurantRepository;
        this.platRepository = platRepository;
    }

    public void mapFromRest(CommandeDTO source, Commande target) {
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        if (source.getDate() != null) {
            target.setDate(source.getDate());
        }
        if (source.getTableNumero() != 0) {
            TableRestaurant tableRestaurant = tableRestaurantRepository.findByNumero(source.getTableNumero()).orElse(null);
            target.setTableRestaurant(tableRestaurant);
        }
        if (source.getPlatQuantities() != null && !source.getPlatQuantities().isEmpty()) {
            Map<Plat, Integer> platQuantities = source.getPlatQuantities().entrySet().stream()
                    .map(entry -> {
                        Plat plat = platRepository.findById(entry.getKey()).orElse(null);
                        return new AbstractMap.SimpleEntry<>(plat, entry.getValue());
                    })
                    .filter(entry -> entry.getKey() != null)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            target.setPlatQuantities(platQuantities);
        }
    }

    public void mapToRest(Commande source, CommandeDTO target) {
        target.setId(source.getId());
        target.setDate(source.getDate());
        if (source.getTableRestaurant() != null) {
            target.setTableNumero(source.getTableRestaurant().getNumero());
        }
        if (source.getPlatQuantities() != null && !source.getPlatQuantities().isEmpty()) {
            // Mapping platQuantities
            Map<Long, Integer> platQuantities = source.getPlatQuantities().entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().getId(), Map.Entry::getValue));
            target.setPlatQuantities(platQuantities);

            // Mapping platNames
            Map<Long, String> platNames = source.getPlatQuantities().keySet().stream()
                    .collect(Collectors.toMap(
                            Plat::getId,
                            plat -> platRepository.findById(plat.getId()).map(Plat::getPlatName).orElse("Nom inconnu")
                    ));
            target.setPlatNames(platNames);
        }
        if (source.getPlatQuantities() != null) {
            Float totalPrice = source.getPlatQuantities().entrySet().stream()
                    .map(entry -> entry.getKey().getPrice() * entry.getValue())
                    .reduce(0.0f, Float::sum);
            target.setTotalPrice(totalPrice);
        }
    }
}