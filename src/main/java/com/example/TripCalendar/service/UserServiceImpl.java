package com.example.TripCalendar.service;

import com.example.TripCalendar.dto.LoginDTO;
import com.example.TripCalendar.dto.RegisterDTO;
import com.example.TripCalendar.entity.UserEntity;
import com.example.TripCalendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserEntity register(RegisterDTO form) {
        if (userRepository.findByUsername(form.getUsername()).isPresent()) {
            log.info("아이디 중복");
            return null;
        }

        UserEntity saved = userRepository.save(form.toEntity());
        log.info("가입됨");
        return saved;
    }

    @Override
    public UserEntity login(LoginDTO form) {
        UserEntity user = userRepository.findByUsername(form.getUsername())
                .filter(u -> u.getPassword().equals(form.getPassword()))
                .orElse(null);

        log.info(user != null ? "로그인 성공" : "로그인 실패");
        return user;
    }
}
