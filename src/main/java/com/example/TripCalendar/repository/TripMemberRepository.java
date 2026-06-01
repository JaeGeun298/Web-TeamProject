package com.example.TripCalendar.repository;

import com.example.TripCalendar.entity.TripMember;
import com.example.TripCalendar.entity.Trip;
import com.example.TripCalendar.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripMemberRepository extends JpaRepository<TripMember, Long> {
    List<TripMember> findByTrip(Trip trip);
    List<TripMember> findByUser(UserEntity user);
}
