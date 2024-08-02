package com.team.building.mappers;

import com.team.building.dtos.FactureDTO;
import com.team.building.model.Commande;
import com.team.building.model.Facture;
import com.team.building.model.Plat;
import com.team.building.repository.CommandeRepository;
import com.team.building.repository.PlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component

public class FactureMapper {
    private final PlatRepository platRepository;
    private final CommandeRepository commandeRepository;

    @Autowired
    public FactureMapper(PlatRepository platRepository, CommandeRepository commandeRepository) {
        this.platRepository = platRepository;
        this.commandeRepository = commandeRepository;
    }

    // Map FactureDTO to Facture
    public void mapFromRest(FactureDTO source, Facture target) {
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        if (source.getDate() != null) {
            target.setDate(source.getDate());
        }
        if (source.getTotalAmount() != null) {
            target.setTotalAmount(source.getTotalAmount());
        }
        if (source.getCommandeId() != null) {
            Commande commande = commandeRepository.findById(source.getCommandeId()).orElse(null);
            target.setCommande(commande);
        }
        if (source.getPlatQuantities() != null && !source.getPlatQuantities().isEmpty()) {
            Map<Plat, Integer> platQuantities = new HashMap<>();
            for (Map.Entry<Long, Integer> entry : source.getPlatQuantities().entrySet()) {
                Plat plat = platRepository.findById(entry.getKey()).orElse(null);
                if (plat != null) {
                    platQuantities.put(plat, entry.getValue());
                }
            }
            target.setPlatQuantities(platQuantities);
        }
    }

    // Map Facture to FactureDTO
    public void mapToRest(Facture source, FactureDTO target) {
        target.setId(source.getId());
        target.setDate(source.getDate());
        target.setTotalAmount(source.getTotalAmount());
        if (source.getCommande() != null) {
            target.setCommandeId(source.getCommande().getId());
        }
        if (source.getPlatQuantities() != null && !source.getPlatQuantities().isEmpty()) {
            Map<Long, Integer> platQuantities = source.getPlatQuantities().entrySet().stream()
                    .collect(Collectors.toMap(plat -> plat.getKey().getId(), Map.Entry::getValue));
            target.setPlatQuantities(platQuantities);
        }
    }
}