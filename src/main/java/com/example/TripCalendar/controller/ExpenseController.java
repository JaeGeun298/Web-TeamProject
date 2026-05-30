package com.example.TripCalendar.controller;

import com.example.TripCalendar.entity.Expense;
import com.example.TripCalendar.entity.Trip;
import com.example.TripCalendar.repository.ExpenseRepository;
import com.example.TripCalendar.service.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final TripService tripService;
    private static final List<String> CATEGORIES = List.of("식비", "교통", "숙박", "쇼핑", "관광", "기타");

    public ExpenseController(ExpenseRepository expenseRepository, TripService tripService) {
        this.expenseRepository = expenseRepository;
        this.tripService = tripService;
    }

    private boolean isNotLoggedIn(HttpSession session) {
        return session.getAttribute("loginUser") == null;
    }

    @GetMapping("/trip/{tripId}")
    public String getExpenses(@PathVariable Long tripId,
                              @RequestParam(required = false) String date,
                              @RequestParam(required = false) String category,
                              HttpSession session,
                              Model model) {
        if (isNotLoggedIn(session)) return "redirect:/auth/login";

        List<Expense> expenses;
        if (date != null && !date.isEmpty() && category != null && !category.isEmpty()) {
            expenses = expenseRepository.findByTripIdAndTravelDateAndCategory(tripId, date, category);
        } else if (date != null && !date.isEmpty()) {
            expenses = expenseRepository.findByTripIdAndTravelDate(tripId, date);
        } else if (category != null && !category.isEmpty()) {
            expenses = expenseRepository.findByTripIdAndCategory(tripId, category);
        } else {
            expenses = expenseRepository.findByTripId(tripId);
        }

        Trip trip = tripService.getTrip(tripId);
        boolean isSettled = trip.isSettled();

        model.addAttribute("loginUser", session.getAttribute("loginUser"));
        model.addAttribute("expenses", expenses);
        model.addAttribute("tripId", tripId);
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("isSettled", isSettled);
        
        List<Map<String, Object>> categoryList = CATEGORIES.stream()
                .map(cat -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", cat);
                    map.put("selected", cat.equals(category));
                    return map;
                }).collect(Collectors.toList());
        model.addAttribute("categories", categoryList);

        int total = expenses.stream().mapToInt(Expense::getPrice).sum();
        model.addAttribute("totalPrice", total);

        Map<String, Integer> summaryMap = new HashMap<>();
        for (String cat : CATEGORIES) summaryMap.put(cat, 0);
        for (Expense e : expenses) {
            if (e.getCategory() != null) {
                summaryMap.put(e.getCategory(), summaryMap.getOrDefault(e.getCategory(), 0) + e.getPrice());
            }
        }
        
        List<Map<String, Object>> summary = summaryMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("key", entry.getKey());
                    map.put("value", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
        model.addAttribute("summary", summary);

        return "expense/list";
    }

    @PostMapping("/create")
    public String createExpense(@ModelAttribute Expense expense, HttpSession session) {
        if (isNotLoggedIn(session)) return "redirect:/auth/login";

        Trip trip = tripService.getTrip(expense.getTripId());
        if (trip.isSettled()) return "redirect:/expenses/trip/" + expense.getTripId();
        
        expenseRepository.save(expense);
        return "redirect:/expenses/trip/" + expense.getTripId() + "?date=" + (expense.getTravelDate() != null ? expense.getTravelDate() : "");
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        if (isNotLoggedIn(session)) return "redirect:/auth/login";

        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid expense Id:" + id));
        Trip trip = tripService.getTrip(expense.getTripId());
        if (trip.isSettled()) return "redirect:/expenses/trip/" + expense.getTripId();

        model.addAttribute("expense", expense);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("loginUser", session.getAttribute("loginUser"));
        
        return "expense/edit";
    }

    @PostMapping("/{id}/update")
    public String updateExpense(@PathVariable Long id, @ModelAttribute Expense expenseDetails, HttpSession session) {
        if (isNotLoggedIn(session)) return "redirect:/auth/login";

        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid expense Id:" + id));
        Trip trip = tripService.getTrip(expense.getTripId());
        if (trip.isSettled()) return "redirect:/expenses/trip/" + expense.getTripId();

        expense.setTitle(expenseDetails.getTitle());
        expense.setPrice(expenseDetails.getPrice());
        expense.setCategory(expenseDetails.getCategory());
        expense.setPayer(expenseDetails.getPayer());
        expense.setTravelDate(expenseDetails.getTravelDate());
        expenseRepository.save(expense);
        return "redirect:/expenses/trip/" + expense.getTripId();
    }

    @PostMapping("/{id}/delete")
    public String deleteExpense(@PathVariable Long id, @RequestParam Long tripId, HttpSession session) {
        if (isNotLoggedIn(session)) return "redirect:/auth/login";

        Trip trip = tripService.getTrip(tripId);
        if (trip.isSettled()) return "redirect:/expenses/trip/" + tripId;

        expenseRepository.deleteById(id);
        return "redirect:/expenses/trip/" + tripId;
    }
}