package com.jinho.spring_security_practice.repository;

import com.jinho.spring_security_practice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
}
