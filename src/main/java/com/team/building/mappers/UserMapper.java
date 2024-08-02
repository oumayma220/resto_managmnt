package com.team.building.mappers;


import com.team.building.dtos.UserDTO;

import com.team.building.model.User;
import org.springframework.stereotype.Component;

@Component

public class UserMapper {
    public void mapFromRest(UserDTO source, User target){
        target.setId(source.getId());
        if(source.getEmail() != null)
            target.setEmail(source.getEmail());
        if(source.getFullname() != null)
            target.setFullname(source.getFullname());
        if(source.getRole() != null)
            target.setRole(source.getRole());
    }
    public void mapToRest(User source, UserDTO target){
        target.setId(source.getId());
        target.setEmail(source.getEmail());
        target.setFullname(source.getFullname());
        target.setRole(source.getRole());
    }
}
