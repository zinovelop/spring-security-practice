package com.jinho.spring_security_practice.service;

import com.jinho.spring_security_practice.dto.JoinDTO;
import com.jinho.spring_security_practice.entity.UserEntity;
import com.jinho.spring_security_practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void joinProcess(JoinDTO joinDTO) {
        if(userRepository.existsByUsername(joinDTO.getUsername())) return;

        UserEntity nubby = new UserEntity(joinDTO.getUsername(), passwordEncoder.encode(joinDTO.getPassword()), "ROLE_USER");
        userRepository.save(nubby);
    }
}
