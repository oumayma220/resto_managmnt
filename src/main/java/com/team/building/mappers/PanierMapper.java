package com.team.building.mappers;

import com.team.building.dtos.PanierDTO;
import com.team.building.model.Panier;
import com.team.building.model.Plat;
import com.team.building.repository.PlatRepository;
import com.team.building.service.PlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component

public class PanierMapper {

    private final PlatService platService;

    @Autowired
    public PanierMapper(PlatService platService) {
        this.platService = platService;
    }

    public void mapFromRest(PanierDTO source, Panier target) {
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        if (source.getPlatQuantities() != null) {
            Map<Plat, Integer> platQuantities = new HashMap<>();
            for (Map.Entry<Long, Integer> entry : source.getPlatQuantities().entrySet()) {
                Plat plat = platService.getPlatById(entry.getKey()); // Obtenez le plat par son ID
                if (plat != null) {
                    platQuantities.put(plat, entry.getValue());
                }
            }
            target.setPlatQuantities(platQuantities);
        }
    }

    public void mapToRest(Panier source, PanierDTO target) {
        target.setId(source.getId());

        if (source.getPlatQuantities() != null) {
            Map<Long, Integer> platQuantities = new HashMap<>();
            Map<Long, String> platNames = new HashMap<>();
            Map<Long, Float> platPrices = new HashMap<>();
            for (Map.Entry<Plat, Integer> entry : source.getPlatQuantities().entrySet()) {
                Plat plat = entry.getKey();
                platQuantities.put(plat.getId(), entry.getValue());
                platNames.put(plat.getId(), plat.getPlatName()); // Ajoutez le nom du plat
                platPrices.put(plat.getId(), plat.getPrice()); // Ajoutez le prix du plat
            }
            target.setPlatQuantities(platQuantities);
            target.setPlatNames(platNames);
            target.setPlatPrices(platPrices);
        }

        target.setTotalPrice(source.calculateTotal());
    }
}