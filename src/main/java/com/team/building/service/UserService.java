package com.team.building.service;

import com.team.building.JWT.JwtProvider;
import com.team.building.Request.RequestedBody;
import com.team.building.dtos.UserDTO;
import com.team.building.mappers.UserMapper;
import com.team.building.model.User;
import com.team.building.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

@Autowired
    UserMapper userMapper;

    public String autenticate(RequestedBody loginRequest){
        String jwt = null;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if(user.isPresent())
            jwt = jwtProvider.generateJwtToken(authentication);
        return jwt;

    }


    public UserDTO getById(Long id) {
        UserDTO userDTO = new UserDTO();
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userMapper.mapToRest(user.get(), userDTO);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        return userDTO;
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
    public User saveStudent(User user) {
        return userRepository.save(user);
    }


    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
    public void updateUserDetails(Long userId, String fullName, String email) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFullname(fullName);
            user.setEmail(email);
            userRepository.save(user);
        }
    }

    public void sendPasswordResetToken(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userRepository.save(user);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Password Reset Request");
            message.setText("To reset your password, use this token: " + token);
            emailSender.send(message);
        } else {
            throw new IllegalArgumentException("Email address not found: " + email);
        }
    }


    // Méthode pour réinitialiser le mot de passe
    public boolean resetPassword(String token, String newPassword) {
        Optional<User> optionalUser = userRepository.findByResetToken(token);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(bcryptEncoder.encode(newPassword));
            user.setResetToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    public void blockUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBlocked(true); // Marquer l'utilisateur comme bloqué
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }
    public void unblockUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBlocked(false); // Définir l'utilisateur comme non bloqué
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID: " + userId);
        }
    }


}
