package com.team.building.dtos;

import java.util.Map;

public class PanierDTO {
    private Long id;
    private Map<Long, Integer> platQuantities; // Map<ID du Plat, Quantité du Plat>
    private Map<Long, String> platNames; // Map<ID du Plat, Nom du Plat>
    private Map<Long, Float> platPrices; // Map<ID du Plat, Prix du Plat>

    private Float totalPrice;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, Integer> getPlatQuantities() {
        return platQuantities;
    }

    public void setPlatQuantities(Map<Long, Integer> platQuantities) {
        this.platQuantities = platQuantities;
    }

    public Map<Long, String> getPlatNames() {
        return platNames;
    }

    public void setPlatNames(Map<Long, String> platNames) {
        this.platNames = platNames;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<Long, Float> getPlatPrices() {
        return platPrices;
    }

    public void setPlatPrices(Map<Long, Float> platPrices) {
        this.platPrices = platPrices;
    }
}
