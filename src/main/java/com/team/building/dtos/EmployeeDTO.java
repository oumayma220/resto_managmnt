package com.team.building.dtos;

import com.team.building.enums.RoleEnum;


public class EmployeeDTO extends UserDTO{
    private long id;
    private String email;
    private String fullname;
    private RoleEnum role;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getFullname() {
        return fullname;
    }

    @Override
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public RoleEnum getRole() {
        return role;
    }

    @Override
    public void setRole(RoleEnum role) {
        this.role = role;
    }
}

