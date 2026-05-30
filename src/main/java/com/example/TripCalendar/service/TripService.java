package com.example.TripCalendar.service;

import com.example.TripCalendar.dto.TripFormDTO;
import com.example.TripCalendar.entity.Trip;
import com.example.TripCalendar.entity.TripMember;
import com.example.TripCalendar.entity.UserEntity;
import com.example.TripCalendar.repository.TripMemberRepository;
import com.example.TripCalendar.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {

    private final TripRepository tripRepository;
    private final TripMemberRepository tripMemberRepository;

    // 여행 생성
    public Trip createTrip(TripFormDTO form, UserEntity user) {
        Trip trip = new Trip();
        trip.setTitle(form.getTitle());
        trip.setStartDate(form.getStartDate());
        trip.setEndDate(form.getEndDate());
        trip.setDescription(form.getDescription());
        trip.setUser(user);

        Trip saved = tripRepository.save(trip);

        // 만든 사람 자동으로 참여자 추가
        TripMember tripMember = new TripMember();
        tripMember.setTrip(saved);
        tripMember.setUser(user);
        tripMemberRepository.save(tripMember);

        return saved;
    }

    // 내 여행 목록
    @Transactional(readOnly = true)
    public List<Trip> getMyTrips(UserEntity user) {
        return tripRepository.findByUser(user);
    }

    // 여행 단건 조회
    @Transactional(readOnly = true)
    public Trip getTrip(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 여행입니다."));
    }

    // 여행 수정
    public void updateTrip(Long id, TripFormDTO form) {
        Trip trip = getTrip(id);
        trip.setTitle(form.getTitle());
        trip.setStartDate(form.getStartDate());
        trip.setEndDate(form.getEndDate());
        trip.setDescription(form.getDescription());
    }

    // 여행 삭제
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    // 멤버 초대
    public void addMember(Long tripId, UserEntity user) {
        Trip trip = getTrip(tripId);
        TripMember tripMember = new TripMember();
        tripMember.setTrip(trip);
        tripMember.setUser(user);
        tripMemberRepository.save(tripMember);
    }

    // 멤버 탈퇴
    public void removeMember(Long tripMemberId) {
        tripMemberRepository.deleteById(tripMemberId);
    }
}
