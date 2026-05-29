package com.example.TripCalendar.controller;

import com.example.TripCalendar.entity.Expense;
import com.example.TripCalendar.repository.ExpenseRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseApiController {

    private final ExpenseRepository expenseRepository;

    public ExpenseApiController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/trip/{tripId}")
    public List<Map<String, Object>> getCalendarExpenses(@PathVariable Long tripId) {
        List<Expense> expenses = expenseRepository.findByTripId(tripId);
        return expenses.stream().map(expense -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", expense.getTitle() + " (" + expense.getPrice() + ")");
            event.put("start", expense.getTravelDate());
            event.put("color", "#ff9f89");
            return event;
        }).collect(Collectors.toList());
    }
}