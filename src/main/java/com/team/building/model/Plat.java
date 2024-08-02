package com.team.building.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "plat")

public class Plat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "platname", unique = true)
    private String platName;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Float price;
    @Lob
    @Column(name="image",length=512)
    private String image;
    @ManyToMany(mappedBy = "plats")
    @JsonBackReference
    private Set<Commande> commandes;
    public Plat(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() { // Use Float instead of float
        return price;
    }

    public void setPrice(Float price) { // Use Float instead of float
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Set<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }
}
