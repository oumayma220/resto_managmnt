package com.team.building.model;

import com.team.building.enums.RoleEnum;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee extends User{

    public Employee(String email, String password, String fullname, String status) {
        super(email, password, fullname, RoleEnum.EMPLOYEE);
    }

    public Employee() {
    }

}
