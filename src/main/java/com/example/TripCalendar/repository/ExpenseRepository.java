package com.example.TripCalendar.repository;

import com.example.TripCalendar.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByTripId(Long tripId);
    List<Expense> findByTripIdAndTravelDate(Long tripId, String travelDate);
    List<Expense> findByTripIdAndCategory(Long tripId, String category);
    List<Expense> findByTripIdAndTravelDateAndCategory(Long tripId, String travelDate, String category);
}