package com.team.building.repository;

import com.team.building.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    public Admin findAdminByEmail(String email);
    public Optional<Admin> findById(Long id);
}
