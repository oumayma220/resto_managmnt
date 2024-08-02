package com.team.building.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "factures")
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "total_amount")
    private Float totalAmount;

    @OneToOne
    @JoinColumn(name = "commande_id")
    @JsonBackReference
    private Commande commande;
    @ElementCollection
    @CollectionTable(name = "facture_plat_quantity", joinColumns = @JoinColumn(name = "facture_id"))
    @MapKeyJoinColumn(name = "plat_id")
    @Column(name = "quantity")
    private Map<Plat, Integer> platQuantities = new HashMap<>();



    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Map<Plat, Integer> getPlatQuantities() {
        return platQuantities;
    }

    public void setPlatQuantities(Map<Plat, Integer> platQuantities) {
        this.platQuantities = platQuantities;
    }
}