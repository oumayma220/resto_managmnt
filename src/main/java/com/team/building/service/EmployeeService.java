package com.team.building.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.encoder.EchoEncoder;
import com.team.building.JWT.JwtProvider;
import com.team.building.Request.CustomErrorType;
import com.team.building.enums.RoleEnum;
import com.team.building.mappers.EmployeeMapper;
import com.team.building.model.Employee;
import com.team.building.model.User;
import com.team.building.repository.AdminRepository;
import com.team.building.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {



}
