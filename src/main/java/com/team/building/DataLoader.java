package com.team.building;

import com.team.building.model.Admin;
import com.team.building.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@org.springframework.stereotype.Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    private void LoadDatabase() {

        Admin admin = new Admin("email@test.com", "123456", "User Tester");
        admin.setPassword(bcryptEncoder.encode(admin.getPassword()));

        if (adminRepository.findAdminByEmail(admin.getEmail()) == null) {
            adminRepository.saveAndFlush(admin);
        }
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        LoadDatabase();
    }
}