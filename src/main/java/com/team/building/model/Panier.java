package com.team.building.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity


public class Panier {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ElementCollection
        @CollectionTable(name = "panier_plat_quantity", joinColumns = @JoinColumn(name = "panier_id"))
        @MapKeyJoinColumn(name = "plat_id")
        @Column(name = "quantity")
        private Map<Plat, Integer> platQuantities = new HashMap<>();

        public void addPlat(Plat plat, int quantity) {
            platQuantities.put(plat, platQuantities.getOrDefault(plat, 0) + quantity);
        }

        public void removePlat(Plat plat) {
            platQuantities.remove(plat);
        }

        public void updatePlatQuantity(Plat plat, int quantity) {
            if (platQuantities.containsKey(plat)) {
                platQuantities.put(plat, quantity);
            }
        }

        public float calculateTotal() {
            float total = 0.0f;
            for (Map.Entry<Plat, Integer> entry : platQuantities.entrySet()) {
                total += entry.getKey().getPrice() * entry.getValue();
            }
            return total;
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Plat, Integer> getPlatQuantities() {
        return platQuantities;
    }

    public void setPlatQuantities(Map<Plat, Integer> platQuantities) {
        this.platQuantities = platQuantities;
    }
}

