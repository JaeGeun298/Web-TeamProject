package com.example.TripCalendar.controller;

import com.example.TripCalendar.entity.Expense;
import com.example.TripCalendar.repository.ExpenseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/trip/{tripId}")
    public String getExpenses(@PathVariable Long tripId,
                              @RequestParam(required = false) String date,
                              HttpSession session,
                              Model model) {
        List<Expense> expenses;
        if (date != null && !date.isEmpty()) {
            expenses = expenseRepository.findByTripIdAndTravelDate(tripId, date);
            model.addAttribute("selectedDate", date);
        } else {
            expenses = expenseRepository.findByTripId(tripId);
        }

        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }

        model.addAttribute("expenses", expenses);
        model.addAttribute("tripId", tripId);
        return "expense/list";
    }

    @PostMapping("/create")
    public String createExpense(@ModelAttribute Expense expense) {
        expenseRepository.save(expense);
        return "redirect:/expenses/trip/" + expense.getTripId() + "?date=" + expense.getTravelDate();
    }

    @PostMapping("/{id}/delete")
    public String deleteExpense(@PathVariable Long id, @RequestParam Long tripId) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        String dateParam = "";
        if (expense != null) {
            dateParam = "?date=" + expense.getTravelDate();
            expenseRepository.deleteById(id);
        }
        return "redirect:/expenses/trip/" + tripId + dateParam;
    }
}