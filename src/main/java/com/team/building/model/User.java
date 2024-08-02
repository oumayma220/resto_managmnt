package com.team.building.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.building.enums.RoleEnum;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @Column(name = "reset_token")
    private String resetToken;
    private boolean blocked;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference

    private List<TableRestaurant> tables;
    public List<TableRestaurant> getTables() {
        return tables;
    }

    public void setTables(List<TableRestaurant> tables) {
        this.tables = tables;
    }


    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonBackReference
    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
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
    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public User(long id, String email, String password, String fullname, RoleEnum role, String resetToken, boolean blocked, List<TableRestaurant> tables) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.resetToken = resetToken;
        this.blocked = blocked;
        this.tables = tables;
    }

    public User(String email, String password, String fullname, RoleEnum role) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    public User(User user) {
        this.id = user.id;
        this.email = user.email;
        this.password = user.password;
        this.fullname = user.fullname;
        this.role = user.role;
    }

    public User() {
    }
}