package com.example.TripCalendar.repository;

import com.example.TripCalendar.entity.Fund;
import com.example.TripCalendar.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FundRepository extends JpaRepository<Fund, Long> {
    List<Fund> findByTrip(Trip trip);
}