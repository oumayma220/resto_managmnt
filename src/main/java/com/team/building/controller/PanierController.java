package com.team.building.controller;

import com.team.building.dtos.CommandeDTO;
import com.team.building.dtos.FactureDTO;
import com.team.building.dtos.PanierDTO;
import com.team.building.model.Commande;
import com.team.building.model.Facture;
import com.team.building.model.Panier;
import com.team.building.model.Plat;
import com.team.building.service.PanierService;
import com.team.building.service.PlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rest/paniers")
public class PanierController {
    private final PanierService panierService;

    @Autowired
    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

    @PostMapping("/create")
    public ResponseEntity<PanierDTO> createPanier() {
        PanierDTO panierDTO = panierService.createPanier();
        return ResponseEntity.ok(panierDTO);
    }

    @PostMapping("/addPlat")
    public ResponseEntity<PanierDTO> addPlatToPanier(@RequestParam Long panierId, @RequestParam Long platId, @RequestParam int quantity) {
        PanierDTO panierDTO = panierService.addPlatToPanier(panierId, platId, quantity);
        return ResponseEntity.ok(panierDTO);
    }

    @DeleteMapping("/removePlat")
    public ResponseEntity<PanierDTO> removePlatFromPanier(@RequestParam Long panierId, @RequestParam Long platId) {
        PanierDTO panierDTO = panierService.removePlatFromPanier(panierId, platId);
        return ResponseEntity.ok(panierDTO);
    }

    @PutMapping("/updatePlatQuantity")
    public ResponseEntity<PanierDTO> updatePlatQuantityInPanier(@RequestParam Long panierId, @RequestParam Long platId, @RequestParam int quantity) {
        PanierDTO panierDTO = panierService.updatePlatQuantityInPanier(panierId, platId, quantity);
        return ResponseEntity.ok(panierDTO);
    }

    @GetMapping("/calculateTotal")
    public ResponseEntity<Float> calculateTotal(@RequestParam Long panierId) {
        Float total = panierService.calculateTotal(panierId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PanierDTO> getPanierById(@PathVariable Long id) {
        PanierDTO panierDTO = panierService.getPanierById(id);
        return ResponseEntity.ok(panierDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable Long id) {
        panierService.deletePanier(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/convertToCommande")
    public ResponseEntity<CommandeDTO> convertPanierToCommande(@RequestParam Long panierId, @RequestParam Long tableId) {
        CommandeDTO commandeDTO = panierService.convertPanierToCommande(panierId, tableId);
        return ResponseEntity.ok(commandeDTO);
    }


}