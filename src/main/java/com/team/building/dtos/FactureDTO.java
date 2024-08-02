package com.team.building.dtos;

import java.util.Date;
import java.util.Map;

public class FactureDTO {

    private Long id;
    private Date date;
    private Float totalAmount;
    private Long commandeId;
    private Map<Long, Integer> platQuantities; // Map of Plat ID to Quantity

    // Constructors
    public FactureDTO() {
    }

    public FactureDTO(Long id, Date date, Float totalAmount, Long commandeId, Map<Long, Integer> platQuantities) {
        this.id = id;
        this.date = date;
        this.totalAmount = totalAmount;
        this.commandeId = commandeId;
        this.platQuantities = platQuantities;
    }

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

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public Map<Long, Integer> getPlatQuantities() {
        return platQuantities;
    }

    public void setPlatQuantities(Map<Long, Integer> platQuantities) {
        this.platQuantities = platQuantities;
    }
}