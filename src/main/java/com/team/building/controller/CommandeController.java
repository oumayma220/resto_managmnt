package com.team.building.controller;

import com.team.building.dtos.CommandeDTO;
import com.team.building.dtos.FactureDTO;
import com.team.building.mappers.CommandeMapper;
import com.team.building.mappers.FactureMapper;
import com.team.building.model.Commande;
import com.team.building.model.Facture;
import com.team.building.service.CommandeService;
import com.team.building.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/rest/commandes")
public class CommandeController {
    @Autowired
    private FactureService factureService;

    private final CommandeService commandeService;
    private final CommandeMapper commandeMapper;
    private final FactureMapper factureMapper;

    @Autowired
    public CommandeController(CommandeService commandeService, CommandeMapper commandeMapper, FactureMapper factureMapper) {
        this.commandeService = commandeService;
        this.commandeMapper = commandeMapper;
        this.factureMapper = factureMapper;
    }
    @PostMapping("/{commandeId}/confirm")
    public ResponseEntity<CommandeDTO> confirmCommande(@PathVariable Long commandeId) {
        CommandeDTO commandeDTO = commandeService.confirmCommande(commandeId);
        return ResponseEntity.ok(commandeDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<CommandeDTO> createCommande(@RequestParam Long tableId) {
        CommandeDTO commandeDTO = commandeService.createCommande(tableId);
        return ResponseEntity.ok(commandeDTO);
    }

    @GetMapping("/{commandeId}")
    public ResponseEntity<CommandeDTO> getCommandeById(@PathVariable Long commandeId) {
        CommandeDTO commandeDTO = commandeService.getCommandeById(commandeId);
        return ResponseEntity.ok(commandeDTO);
    }
    @PostMapping("/{commandeId}/generateFacture")
    public ResponseEntity<FactureDTO> generateFacture(@PathVariable Long commandeId) {
        FactureDTO factureDTO = factureService.generateFactureDTO(commandeId);
        return ResponseEntity.ok(factureDTO);
    }
    @GetMapping("/revenu")
    public ResponseEntity<?> getRevenuByTableForDate(
            @RequestParam Long tableId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            double revenu = commandeService.getRevenuByTableForDate(tableId, date);
            return ResponseEntity.ok(revenu);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }






}