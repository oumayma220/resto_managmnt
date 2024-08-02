package com.team.building.service;

import com.team.building.dtos.CommandeDTO;
import com.team.building.dtos.PanierDTO;
import com.team.building.mappers.PanierMapper;
import com.team.building.model.*;
import com.team.building.repository.PanierRepository;
import com.team.building.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PanierService {
    private final PanierRepository panierRepository;
    private final PanierMapper panierMapper;
    private final PlatService platService;
    private final CommandeService commandeService;
    private final UserRepository userRepository ;

    @Autowired
    public PanierService(PanierRepository panierRepository, PanierMapper panierMapper, PlatService platService, CommandeService commandeService, UserRepository userRepository) {
        this.panierRepository = panierRepository;
        this.panierMapper = panierMapper;
        this.platService = platService;
        this.commandeService = commandeService;
        this.userRepository = userRepository;
    }

    private PanierDTO convertToDTO(Panier panier) {
        PanierDTO panierDTO = new PanierDTO();
        panierMapper.mapToRest(panier, panierDTO);
        return panierDTO;
    }

    public PanierDTO createPanier() {
        Panier panier = new Panier();
        panier = panierRepository.save(panier);
        return convertToDTO(panier);
    }
    public PanierDTO addPlatToPanier(Long panierId, Long platId, int quantity) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier not found"));
        Plat plat = platService.getPlatById(platId);
        panier.addPlat(plat, quantity);
        panier = panierRepository.save(panier);
        return convertToDTO(panier);
    }

    public PanierDTO removePlatFromPanier(Long panierId, Long platId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier not found"));
        Plat plat = platService.getPlatById(platId);
        panier.removePlat(plat);
        panier = panierRepository.save(panier);
        return convertToDTO(panier);
    }

    public PanierDTO updatePlatQuantityInPanier(Long panierId, Long platId, int quantity) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier not found"));
        Plat plat = platService.getPlatById(platId);
        panier.updatePlatQuantity(plat, quantity);
        panier = panierRepository.save(panier);
        return convertToDTO(panier);
    }

    public Float calculateTotal(Long panierId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier not found"));
        return panier.calculateTotal();
    }

    public PanierDTO getPanierById(Long id) {
        Panier panier = panierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Panier not found"));
        return convertToDTO(panier);
    }

    public void deletePanier(Long id) {
        if (!panierRepository.existsById(id)) {
            throw new IllegalArgumentException("Panier not found");
        }
        panierRepository.deleteById(id);
    }
    public void emptyPanier(Long panierId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier not found"));

        panier.getPlatQuantities().clear(); // Vider les plats du panier
        panierRepository.save(panier); // Sauvegarder les changements
    }
    public CommandeDTO convertPanierToCommande(Long panierId, Long tableId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier not found"));

        // Création de la commande
        CommandeDTO commandeDTO = commandeService.createCommande(tableId);

        // Ajout des plats du panier à la commande
        for (Map.Entry<Plat, Integer> entry : panier.getPlatQuantities().entrySet()) {
            Plat plat = entry.getKey();
            int quantity = entry.getValue();
            try {
                commandeService.addPlatToCommande(commandeDTO.getId(), plat.getId(), quantity);
            } catch (Exception e) {
                System.err.println("Error adding plat to commande: " + e.getMessage());
            }
        }

        // Vider le panier après conversion
        emptyPanier(panierId);

        // Retourner les détails de la commande
        return commandeService.getCommandeById(commandeDTO.getId());
    }


}