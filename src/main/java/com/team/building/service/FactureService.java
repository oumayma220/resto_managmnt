package com.team.building.service;

import com.team.building.dtos.FactureDTO;
import com.team.building.mappers.FactureMapper;
import com.team.building.model.Commande;
import com.team.building.model.Facture;
import com.team.building.model.Plat;
import com.team.building.repository.CommandeRepository;
import com.team.building.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class FactureService {
    private final FactureRepository factureRepository;
    private final CommandeRepository commandeRepository;
    private final FactureMapper factureMapper;

    @Autowired
    public FactureService(FactureRepository factureRepository, CommandeRepository commandeRepository, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.commandeRepository = commandeRepository;
        this.factureMapper = factureMapper;
    }
    public FactureDTO generateFactureDTO(Long commandeId) {
        // Retrieve the facture associated with the commande
        Facture facture = factureRepository.findByCommandeId(commandeId)
                .orElseThrow(() -> new RuntimeException("Facture not found for commande ID " + commandeId));

        // Convert the facture to FactureDTO
        FactureDTO factureDTO = new FactureDTO();
        factureMapper.mapToRest(facture, factureDTO);
        return factureDTO;
    }

    public Facture createFacture(Long commandeId) {
        Optional<Commande> commandeOptional = commandeRepository.findById(commandeId);
        if (!commandeOptional.isPresent()) {
            throw new IllegalArgumentException("Commande not found");
        }

        Commande commande = commandeOptional.get();

        Facture facture = new Facture();
        facture.setDate(new Date());
        facture.setCommande(commande);
        facture.setPlatQuantities(commande.getPlatQuantities());

        Float totalAmount = calculateTotalAmount(facture.getPlatQuantities());
        facture.setTotalAmount(totalAmount);

        return factureRepository.save(facture);
    }

    private Float calculateTotalAmount(Map<Plat, Integer> platQuantities) {
        float total = 0.0f;
        for (Map.Entry<Plat, Integer> entry : platQuantities.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
    public Facture getFactureByCommandeId(Long commandeId) {
        return factureRepository.findByCommandeId(commandeId)
                .orElseThrow(() -> new IllegalArgumentException("Facture not found for Commande ID: " + commandeId));
    }
    public Facture getFactureById(Long factureId) {
        return factureRepository.findById(factureId).orElseThrow(() -> new IllegalArgumentException("Facture not found"));
    }

    public Facture updateFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    public void deleteFacture(Long factureId) {
        factureRepository.deleteById(factureId);
    }

    // Other methods...
}