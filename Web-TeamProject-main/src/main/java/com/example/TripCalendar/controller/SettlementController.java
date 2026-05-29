package com.example.TripCalendar.controller;

import com.example.TripCalendar.service.SettlementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/settlements")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @GetMapping("/trip/{tripId}")
    public String getSettlementResult(@PathVariable Long tripId,
                                      @RequestParam(required = false) Integer totalMembers,
                                      HttpSession session,
                                      Model model) {

        String sessionKey = "trip_" + tripId + "_members";
        if (totalMembers != null) {
            session.setAttribute(sessionKey, totalMembers);
        } else {
            Integer cachedMembers = (Integer) session.getAttribute(sessionKey);
            totalMembers = (cachedMembers != null) ? cachedMembers : 1;
        }

        Map<String, Object> settlementData = settlementService.calculateSettlement(tripId, totalMembers);

        @SuppressWarnings("unchecked")
        Map<String, Integer> balances = (Map<String, Integer>) settlementData.get("balances");

        List<Map<String, Object>> balanceList = balances.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("key", entry.getKey());
                    map.put("value", entry.getValue());
                    return map;
                }).collect(Collectors.toList());

        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }

        model.addAttribute("totalExpense", settlementData.get("totalExpense"));
        model.addAttribute("perPerson", settlementData.get("perPerson"));
        model.addAttribute("settlements", balanceList);
        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("tripId", tripId);

        return "settlement/result";
    }
}