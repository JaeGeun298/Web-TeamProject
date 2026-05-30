package com.example.TripCalendar.service;

import com.example.TripCalendar.entity.Expense;
import com.example.TripCalendar.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettlementService {

    private final ExpenseRepository expenseRepository;

    public SettlementService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Map<String, Object> calculateSettlement(Long tripId, int totalMembers) {
        List<Expense> expenses = expenseRepository.findByTripId(tripId);
        Map<String, Integer> payerTotals = new HashMap<>();
        Map<String, Integer> categoryTotals = new HashMap<>();
        int totalExpense = 0;

        for (Expense expense : expenses) {
            int price = expense.getPrice();
            totalExpense += price;
            payerTotals.put(expense.getPayer(), payerTotals.getOrDefault(expense.getPayer(), 0) + price);
            
            String cat = expense.getCategory() != null ? expense.getCategory() : "기타";
            categoryTotals.put(cat, categoryTotals.getOrDefault(cat, 0) + price);
        }

        int perPerson = totalMembers > 0 ? totalExpense / totalMembers : 0;
        Map<String, Integer> balances = new HashMap<>();

        for (Map.Entry<String, Integer> entry : payerTotals.entrySet()) {
            balances.put(entry.getKey(), entry.getValue() - perPerson);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalExpense", totalExpense);
        result.put("perPerson", perPerson);
        result.put("balances", balances);
        result.put("categoryTotals", categoryTotals);
        result.put("expenses", expenses);
        result.put("payerCount", payerTotals.size());

        return result;
    }
}