package com.team.building.mappers;

import com.team.building.dtos.TableRestaurantDTO;
import com.team.building.model.TableRestaurant;
import com.team.building.model.User;
import com.team.building.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class TableRestaurantMapper {
    private UserRepository userRepository;
    @Autowired
    public TableRestaurantMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void mapFromRest(TableRestaurantDTO source, TableRestaurant target) {
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        if (source.getNumero() != null) {
            target.setNumero(source.getNumero());
        }
        if(source.getEtat() != null){
            target.setEtat(source.getEtat());

        }
        if (source.getUserId() != null) {
            User user = userRepository.findById(source.getUserId()).orElse(null);
            target.setUser(user);
        }
    }
    public void mapToRest(TableRestaurant source, TableRestaurantDTO target) {
        target.setId(source.getId());
        target.setNumero(source.getNumero());
        target.setEtat(source.getEtat());
        if (source.getUser() != null) {
            target.setUserId(source.getUser().getId());
            target.setUserName(source.getUser().getFullname()); // Assurez-vous que le champ username existe

        }
    }
}
