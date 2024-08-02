package com.team.building.dtos;

import com.team.building.enums.RoleEnum;
import com.team.building.model.User;

public class AdminDTO extends User {
    private long id;
    private String email;
    private String fullname;
    private RoleEnum role;
    private String createdon;

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

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }
}
