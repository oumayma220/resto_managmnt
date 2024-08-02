package com.team.building.dtos;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class CommandeDTO {
    private Long id;
    private LocalDate date; // Changer de Date à LocalDate
    private int tableNumero; // Changed from tableId to tableNumero
    private Map<Long, Integer> platQuantities; // Utiliser Long pour Plat ID et Integer pour la quantité
    private Map<Long, String> platNames; // Utiliser Map<Long, String> pour associer ID à nom
    private Float totalPrice;


    // Getters and setters
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

    public int getTableNumero() {
        return tableNumero;
    }

    public void setTableNumero(int tableNumero) {
        this.tableNumero = tableNumero;
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
}
