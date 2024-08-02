package com.team.building.service;

import com.team.building.DateConversionUtil;
import com.team.building.dtos.CommandeDTO;
import com.team.building.dtos.FactureDTO;
import com.team.building.mappers.CommandeMapper;
import com.team.building.mappers.FactureMapper;
import com.team.building.model.Commande;
import com.team.building.model.Facture;
import com.team.building.model.Plat;
import com.team.building.model.TableRestaurant;
import com.team.building.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommandeService {


    private final CommandeRepository commandeRepository;
    private final PlatRepository platRepository;
    private final TableRestaurantRepository tableRestaurantRepository;
    private final PlatService platService;
    private final FactureRepository factureRepository;
    private final PanierRepository panierRepository;
    private final CommandeMapper commandeMapper;
    private final FactureMapper factureMapper;

    @Autowired
    public CommandeService(CommandeRepository commandeRepository, PlatRepository platRepository, TableRestaurantRepository tableRestaurantRepository, PlatService platService, FactureRepository factureRepository, PanierRepository panierRepository, CommandeMapper commandeMapper, FactureMapper factureMapper) {
        this.commandeRepository = commandeRepository;
        this.platRepository = platRepository;
        this.tableRestaurantRepository = tableRestaurantRepository;
        this.platService = platService;
        this.factureRepository = factureRepository;
        this.panierRepository = panierRepository;
        this.commandeMapper = commandeMapper;
        this.factureMapper = factureMapper;
    }
    private CommandeDTO convertToDTO(Commande commande) {
        CommandeDTO commandeDTO = new CommandeDTO();
        commandeMapper.mapToRest(commande, commandeDTO);
        return commandeDTO;
    }

    private FactureDTO convertToDTO(Facture facture) {
        FactureDTO factureDTO = new FactureDTO();
        factureMapper.mapToRest(facture, factureDTO);
        return factureDTO;
    }


    public CommandeDTO createCommande(Long tableId) {
        Optional<TableRestaurant> tableRestaurantOptional = tableRestaurantRepository.findById(tableId);
        if (!tableRestaurantOptional.isPresent()) {
            throw new IllegalArgumentException("Table not found");
        }

        Commande commande = new Commande();
        commande.setTableRestaurant(tableRestaurantOptional.get());
        commande.setDate(LocalDate.now()); // Utilisation de LocalDate

        Commande savedCommande = commandeRepository.save(commande);
        return convertToDTO(savedCommande);
    }

    public CommandeDTO addPlatToCommande(Long commandeId, Long platId, int quantity) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new IllegalArgumentException("Commande not found"));

        Plat plat = platRepository.findById(platId)
                .orElseThrow(() -> new IllegalArgumentException("Plat not found"));

        if (commande.getPlatQuantities() == null) {
            commande.setPlatQuantities(new HashMap<>());
        }
        commande.getPlatQuantities().put(plat, quantity);
        Commande savedCommande = commandeRepository.save(commande);
        return convertToDTO(savedCommande);
    }

    public CommandeDTO removePlatFromCommande(Long commandeId, Long platId) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new IllegalArgumentException("Commande not found"));

        Plat plat = platRepository.findById(platId)
                .orElseThrow(() -> new IllegalArgumentException("Plat not found"));

        commande.getPlatQuantities().remove(plat);
        Commande savedCommande = commandeRepository.save(commande);
        return convertToDTO(savedCommande);
    }

    public CommandeDTO saveCommande(CommandeDTO commandeDTO) {
        Commande commande = new Commande();
        commandeMapper.mapFromRest(commandeDTO, commande);
        Commande savedCommande = commandeRepository.save(commande);
        return convertToDTO(savedCommande);
    }

    public CommandeDTO getCommandeById(Long commandeId) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new IllegalArgumentException("Commande not found"));
        return convertToDTO(commande);
    }
    // Convert Commande to CommandeDTO


    // Confirmer la commande et retourner les détails comme une facture
    public CommandeDTO confirmCommande(Long commandeId) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new IllegalArgumentException("Commande not found"));

        // Marquer la commande comme confirmée
        commande.setStatus("PAID");
        commande = commandeRepository.save(commande);

        // Retourner les détails de la commande comme une facture
        return convertToDTO(commande);
    }

    public double getRevenuByTableForDate(Long tableId, LocalDate date) {
        List<Commande> commandes = commandeRepository.findByTableRestaurantIdAndDate(tableId, date);
        double totalRevenu = commandes.stream()
                .filter(commande -> "PAID".equals(commande.getStatus()))
                .mapToDouble(commande -> commande.getPlatQuantities().entrySet().stream()
                        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                        .sum())
                .sum();
        return totalRevenu;
    }





}