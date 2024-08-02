package com.team.building.mappers;

import com.team.building.dtos.EmployeeDTO;

import com.team.building.model.Employee;


public class EmployeeMapper {
    public void mapFromRest(EmployeeDTO source, Employee target){
        target.setId(source.getId());
        if(source.getEmail() != null)
            target.setEmail(source.getEmail());
        if(source.getFullname() != null)
            target.setFullname(source.getFullname());
        if(source.getRole() != null)
            target.setRole(source.getRole());
        if(source.getPassword()!=null)
            target.setPassword(source.getPassword());

    }
    public void mapToRest(Employee source, EmployeeDTO target){
        target.setId(source.getId());
        target.setEmail(source.getEmail());
        target.setFullname(source.getFullname());
        target.setRole(source.getRole());
        target.setPassword(source.getPassword());
    }
}
