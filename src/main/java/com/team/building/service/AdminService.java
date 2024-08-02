package com.team.building.service;

import com.team.building.dtos.AdminDTO;
import com.team.building.mappers.AdminMapper;
import com.team.building.model.Admin;
import com.team.building.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public AdminMapper adminMapper = new AdminMapper();

    public AdminDTO getAdminById(Long id){
        AdminDTO adminDTO = new AdminDTO();
        Optional<Admin> admin = this.adminRepository.findById(id);
        if(admin.isPresent()) {
            this.adminMapper.mapToRest(admin.get(), adminDTO);
        }
        return adminDTO;
    }

}
