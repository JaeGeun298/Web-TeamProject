package com.example.TripCalendar.repository;

import com.example.TripCalendar.entity.Trip;
import com.example.TripCalendar.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUser(UserEntity user);
    List<Trip> findByTripMembers_User(UserEntity user);
}