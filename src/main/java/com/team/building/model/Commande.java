package com.team.building.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "table_id")
    @JsonBackReference
    private TableRestaurant tableRestaurant;

    @ManyToMany
    @JoinTable(
            name = "commande_plat",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "plat_id")
    )
    @JsonManagedReference
    private Set<Plat> plats;
    @ElementCollection
    @CollectionTable(name = "commande_plat_quantity", joinColumns = @JoinColumn(name = "commande_id"))
    @MapKeyJoinColumn(name = "plat_id")
    @Column(name = "quantity")
    private Map<Plat, Integer> platQuantities = new HashMap<>();

    @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
    @JsonManagedReference

    private Facture facture;
    @Column(name = "status")
    private String status= "NOT PAID";

    public void addPlat(Plat plat, int quantity) {
        plats.add(plat);
        platQuantities.put(plat, platQuantities.getOrDefault(plat, 0) + quantity);
    }

    public void removePlat(Plat plat) {
        plats.remove(plat);
        platQuantities.remove(plat);
    }

    public void updatePlatQuantity(Plat plat, int quantity) {
        if (platQuantities.containsKey(plat)) {
            platQuantities.put(plat, quantity);
        }
    }

    public float calculateTotalPrice() {
        float totalPrice = 0.0f;
        for (Map.Entry<Plat, Integer> entry : platQuantities.entrySet()) {
            totalPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return totalPrice;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Commande() {
        this.date = LocalDate.now();
        this.plats = new HashSet<>();
    }

    public TableRestaurant getTableRestaurant() {
        return tableRestaurant;
    }

    public void setTableRestaurant(TableRestaurant tableRestaurant) {
        this.tableRestaurant = tableRestaurant;
    }

    public Set<Plat> getPlats() {
        return plats;
    }

    public void setPlats(Set<Plat> plats) {
        this.plats = plats;
    }

    public Float getTotalPrice() {
        if (plats == null) {
            plats = new HashSet<>();
        }
        return plats.stream()
                .map(Plat::getPrice)
                .reduce(0.0f, Float::sum);
    }

    public Map<Plat, Integer> getPlatQuantities() {
        return platQuantities;
    }

    public void setPlatQuantities(Map<Plat, Integer> platQuantities) {
        this.platQuantities = platQuantities;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}