package com.team.building.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.building.dtos.AdminDTO;
import com.team.building.dtos.PlatDto;
import com.team.building.dtos.TableRestaurantDTO;
import com.team.building.dtos.UserDTO;
import com.team.building.model.Plat;
import com.team.building.model.TableRestaurant;
import com.team.building.model.User;
import com.team.building.service.AdminService;
import com.team.building.service.PlatService;
import com.team.building.service.TableRestaurantService;
import com.team.building.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rest/authenticated")
public class Authenticated {

    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
    @Autowired
    PlatService platService;
    @Autowired
    private TableRestaurantService tableRestaurantService;





    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        try {
            UserDTO userDTO = userService.getById(id);
            return ResponseEntity.ok(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if user does not exist
        }
    }

    @GetMapping("/users")
    public List<User> listUsers(Model model) {
        List<User> users = userService.getAllUsers().stream()
                .filter(user -> user.getId() != 1)
                .collect(Collectors.toList());
        return users;
    }
    @DeleteMapping("/users/{id}")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("Utilisateur supprimé avec succès.");
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUserDetails(@PathVariable("id") Long userId, @RequestBody @Valid UserDTO userDTO) {
        try {
            // Utilisez les détails reçus pour mettre à jour l'utilisateur
            userService.updateUserDetails(userId, userDTO.getFullname(), userDTO.getEmail());
            return ResponseEntity.ok("Détails de l'utilisateur mis à jour avec succès.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour des détails.");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                SecurityContextHolder.clearContext();
            }
            return ResponseEntity.ok("Déconnexion réussie");
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la déconnexion", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/plats")
    public List<Plat> getAllPlats() {
        return platService.getAllPlat();
    }

    @RequestMapping(path = "/plats", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PlatDto> createPlat(@RequestParam("image") String image,
                                              @RequestParam("subject") String subject) {
        try {
            PlatDto response = new ObjectMapper().readValue(subject, PlatDto.class);
            response.setImage(image);
            this.platService.createPlat(response);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/plats/{id}")
    public ResponseEntity<Plat> updatePlat(@PathVariable("id") Long id, @RequestBody PlatDto platDto) {
        try {
            Plat updatedPlat = platService.updatePlat(id, platDto);
            return ResponseEntity.ok(updatedPlat);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/plats/{id}")
    public ResponseEntity<String> deletePlat(@PathVariable("id") Long id) {
        try {
            platService.deletePlat(id);
            return ResponseEntity.ok("Plat supprimé avec succès.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plat non trouvé.");
        }
    }

    @PostMapping("/{userId}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long userId) {
        try {
            userService.blockUserById(userId);
            return ResponseEntity.ok("Utilisateur bloqué avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Ou ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
        }
    }
    @PostMapping("/{userId}/unblock")
    public ResponseEntity<String> unblockUser(@PathVariable Long userId) {
        try {
            userService.unblockUserById(userId);
            return ResponseEntity.ok("Utilisateur débloqué avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Ou ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
        }
    }
    @GetMapping("/plats/{id}")
    public ResponseEntity<Plat> getPlatById(@PathVariable Long id) {
        try {
            Plat plat = platService.getPlatById(id);
            return ResponseEntity.ok(plat);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/create")
    public TableRestaurantDTO createTable(@RequestBody TableRestaurantDTO tableRestaurantDTO) {
        return tableRestaurantService.createTable(tableRestaurantDTO);
    }

    @PostMapping("/{tableId}/associate/{userId}")
    public TableRestaurantDTO associateTableWithUser(@PathVariable Long tableId, @PathVariable Long userId) {
        return tableRestaurantService.associateTableWithUser(tableId, userId);
    }
    @GetMapping("/tables/{id}")
    public ResponseEntity<TableRestaurantDTO> getTableById(@PathVariable Long id) {
        try {
            TableRestaurantDTO table = tableRestaurantService.getTableById(id);
            return ResponseEntity.ok(table);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/tables/{id}")
    public ResponseEntity<TableRestaurantDTO> updateTable(@PathVariable Long id, @RequestBody TableRestaurantDTO tableRestaurantDTO) {
        try {
            TableRestaurantDTO updatedTable = tableRestaurantService.updateTable(id, tableRestaurantDTO);
            return ResponseEntity.ok(updatedTable);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/tables/{id}")
    public ResponseEntity<String> deleteTable(@PathVariable Long id) {
        try {
            tableRestaurantService.deleteTable(id);
            return ResponseEntity.ok("Table supprimée avec succès.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Table non trouvée.");
        }
    }

    @GetMapping("/tables")
    public List<TableRestaurantDTO> getAllTables() {
        return tableRestaurantService.getAllTables();
    }

}