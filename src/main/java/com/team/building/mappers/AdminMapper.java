package com.team.building.mappers;

import com.team.building.dtos.AdminDTO;
import com.team.building.dtos.EmployeeDTO;
import com.team.building.dtos.UserDTO;
import com.team.building.model.Admin;
import com.team.building.model.Employee;
import com.team.building.model.User;
import org.springframework.beans.BeanUtils;

public class AdminMapper {
    public void mapFromRest(AdminDTO source, Admin target){
        target.setId(source.getId());
        if(source.getEmail() != null)
            target.setEmail(source.getEmail());
        if(source.getFullname() != null)
            target.setFullname(source.getFullname());
        if(source.getRole() != null)
            target.setRole(source.getRole());
    }
    public void mapToRest(Admin source, AdminDTO target){
        target.setId(source.getId());
        target.setEmail(source.getEmail());
        target.setFullname(source.getFullname());
        target.setRole(source.getRole());
        target.setCreatedon(source.getCreatedon());
    }



}
