package com.example.TripCalendar.service;

import com.example.TripCalendar.dto.LoginDTO;
import com.example.TripCalendar.dto.RegisterDTO;
import com.example.TripCalendar.entity.Fund;
import com.example.TripCalendar.entity.Trip;
import com.example.TripCalendar.entity.TripMember;
import com.example.TripCalendar.entity.UserEntity;
import com.example.TripCalendar.repository.FundRepository;
import com.example.TripCalendar.repository.TripMemberRepository;
import com.example.TripCalendar.repository.TripRepository;
import com.example.TripCalendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TripMemberRepository tripMemberRepository;
    private final FundRepository fundRepository;
    private final TripRepository tripRepository;

    @Override
    @Transactional
    public UserEntity register(RegisterDTO form) {
        if (userRepository.findByUsername(
                form.getUsername())
                .isPresent()) {
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

    @Override
    @Transactional
    public boolean updatePassword(Long userId, String currentPassword, String newPassword) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null || !user.getPassword().equals(currentPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) return;

        List<TripMember> memberRecords = tripMemberRepository.findByUser(user);
        tripMemberRepository.deleteAll(memberRecords);

        List<Fund> fundRecords = fundRepository.findByUser(user);
        fundRepository.deleteAll(fundRecords);

        List<Trip> ownedTrips = tripRepository.findByUser(user);
        for (Trip trip : ownedTrips) {
            trip.setUser(null);
            tripRepository.save(trip);
        }

        userRepository.deleteById(userId);
        log.info("탈퇴 완료: {}", user.getUsername());
    }
}
