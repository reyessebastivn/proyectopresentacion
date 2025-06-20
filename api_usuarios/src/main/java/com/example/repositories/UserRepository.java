package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    
    User findByEmail(String email);

}
