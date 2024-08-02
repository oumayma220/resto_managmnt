package com.team.building.controller;

import com.team.building.JWT.JwtResponse;
import com.team.building.Request.CustomErrorType;
import com.team.building.Request.RequestedBody;
import com.team.building.dtos.EmployeeDTO;
import com.team.building.enums.RoleEnum;
import com.team.building.model.Employee;
import com.team.building.model.User;
import com.team.building.repository.AdminRepository;
import com.team.building.repository.EmployeeRepository;
import com.team.building.repository.UserRepository;
import com.team.building.service.EmployeeService;
import com.team.building.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rest/notAuthenticated")
public class NotAuthenticated {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    @Autowired
    UserService userService;
    @Autowired
    EmployeeService employeeService ;

    public static final Logger logger = LoggerFactory.getLogger(NotAuthenticated.class);


    @PostMapping("/signin")

    public ResponseEntity<?> authenticateUser(@Valid @RequestBody RequestedBody loginRequest) {
        try{
            String jwt = this.userService.autenticate(loginRequest);
            return ResponseEntity.ok(new JwtResponse(jwt));
        }catch(Exception e){
            return new ResponseEntity("access-denied", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping("/principal")
    public Principal user(Principal principal) {

        return principal;

    }


    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<?> signup(@RequestBody @Valid EmployeeDTO employeeDTO) {
        if (adminRepository.findAdminByEmail(employeeDTO.getEmail()) != null) {
            logger.error("Username already exists: " + employeeDTO.getEmail());
            return new ResponseEntity<>(
                    new CustomErrorType("User with username " + employeeDTO.getEmail() + " already exists."),
                    HttpStatus.CONFLICT);
        } else {
            Employee newEmployee = new Employee();
            newEmployee.setEmail(employeeDTO.getEmail());
            newEmployee.setFullname(employeeDTO.getFullname());
            newEmployee.setPassword(bcryptEncoder.encode(employeeDTO.getPassword()));
            newEmployee.setRole(RoleEnum.EMPLOYEE);

            employeeRepository.saveAndFlush(newEmployee);

            return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            userService.sendPasswordResetToken(email);
            return ResponseEntity.ok("Password reset token sent to your email.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email address not found: " + email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send password reset token.");
        }
    }



    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean result = userService.resetPassword(token, newPassword);
        if (result) {
            return "Password has been reset successfully.";
        } else {
            return "Invalid token.";
        }
    }

}
