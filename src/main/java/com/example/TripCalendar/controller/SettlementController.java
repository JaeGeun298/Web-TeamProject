package com.example.TripCalendar.controller;

import com.example.TripCalendar.entity.Trip;
import com.example.TripCalendar.service.SettlementService;
import com.example.TripCalendar.service.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final TripService tripService;

    public SettlementController(SettlementService settlementService, TripService tripService) {
        this.settlementService = settlementService;
        this.tripService = tripService;
    }

    // 정산 결과 페이지
    @GetMapping("/trip/{tripId}")
    public String getSettlementResult(@PathVariable Long tripId,
                                      @RequestParam(required = false) Integer totalMembers,
                                      HttpSession session,
                                      Model model) {

        String sessionKey = "trip_" + tripId + "_members";
        Trip trip = tripService.getTrip(tripId);
        boolean isSettled = trip.isSettled();
        
        // 정산 설정 인원수 세션(Session) 관리
        if (totalMembers == null) {
            Integer cachedMembers = (Integer) session.getAttribute(sessionKey);
            if (cachedMembers != null) {
                totalMembers = cachedMembers;
            } else {
                Map<String, Object> initialData = settlementService.calculateSettlement(tripId, 1);
                int payerCount = (int) initialData.get("payerCount");
                totalMembers = (payerCount > 0) ? payerCount : 1; 
                session.setAttribute(sessionKey, totalMembers);
            }
        } else {
            session.setAttribute(sessionKey, totalMembers);
        }

        Map<String, Object> settlementData = settlementService.calculateSettlement(tripId, totalMembers);

        @SuppressWarnings("unchecked")
        Map<String, Integer> balances = (Map<String, Integer>) settlementData.get("balances");
        List<Map<String, Object>> balanceList = balances.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("key", entry.getKey());
                    map.put("value", entry.getValue());
                    // UI 조건부 출력을 위한 금액 상태 데이터 가공
                    map.put("isPositive", entry.getValue() > 0);
                    map.put("isNegative", entry.getValue() < 0);
                    map.put("isZero", entry.getValue() == 0);
                    return map;
                }).collect(Collectors.toList());

        @SuppressWarnings("unchecked")
        Map<String, Integer> categoryTotals = (Map<String, Integer>) settlementData.get("categoryTotals");
        List<Map<String, Object>> categoryList = categoryTotals.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("key", entry.getKey());
                    map.put("value", entry.getValue());
                    return map;
                }).collect(Collectors.toList());

        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) model.addAttribute("loginUser", loginUser);

        model.addAttribute("totalExpense", settlementData.get("totalExpense"));
        model.addAttribute("perPerson", settlementData.get("perPerson"));
        model.addAttribute("settlements", balanceList);
        model.addAttribute("categorySummaries", categoryList);
        model.addAttribute("allExpenses", settlementData.get("expenses"));
        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("tripId", tripId);
        model.addAttribute("isSettled", isSettled);

        return "settlement/result";
    }

    @PostMapping("/trip/{tripId}/settle")
    public String markAsSettled(@PathVariable Long tripId) {
        tripService.toggleSettled(tripId, true);
        return "redirect:/settlements/trip/" + tripId;
    }

    @PostMapping("/trip/{tripId}/unsettle")
    public String markAsUnsettled(@PathVariable Long tripId) {
        tripService.toggleSettled(tripId, false);
        return "redirect:/settlements/trip/" + tripId;
    }
}
