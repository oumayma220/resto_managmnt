package com.team.building.repository;

import com.team.building.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  public  Optional<User> findByEmail(String email);
   public Optional<User> findById(Long id);
   public Optional<User> findByResetToken(String resetToken);



}
