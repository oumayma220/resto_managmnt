package com.team.building.dtos;

import com.team.building.enums.RoleEnum;

import javax.annotation.sql.DataSourceDefinition;
public class UserDTO {
    private long id;
    private String email;
    private String fullname;
    private RoleEnum role;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public void setId(long id) {
        this.id=id;
    }
}
