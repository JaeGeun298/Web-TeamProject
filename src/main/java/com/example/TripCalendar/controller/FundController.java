package com.example.TripCalendar.controller;

import com.example.TripCalendar.dto.FundFormDTO;
import com.example.TripCalendar.entity.UserEntity;
import com.example.TripCalendar.service.FundService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trips/{tripId}/funds")
public class FundController {

    private final FundService fundService;

    private UserEntity getLoginUser(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("loginUser");
        if (user == null) throw new IllegalStateException("로그인이 필요합니다.");
        return user;
    }

    // 공금 현황 페이지
    @GetMapping
    public String list(@PathVariable Long tripId, HttpSession session, Model model) {
        getLoginUser(session);
        model.addAttribute("funds", fundService.getFunds(tripId));
        model.addAttribute("totalFund", fundService.getTotalFund(tripId));
        model.addAttribute("tripId", tripId);
        return "fund/list";
    }

    // 공금 납입 처리
    @PostMapping
    public String addFund(@PathVariable Long tripId,
                          @ModelAttribute FundFormDTO form,
                          HttpSession session) {
        UserEntity user = getLoginUser(session);
        fundService.addFund(tripId, form, user);
        return "redirect:/trips/" + tripId + "/funds";
    }

    // 공금 기록 삭제
    @PostMapping("/{fundId}/delete")
    public String deleteFund(@PathVariable Long tripId,
                             @PathVariable Long fundId,
                             HttpSession session) {
        getLoginUser(session);
        fundService.deleteFund(fundId);
        return "redirect:/trips/" + tripId + "/funds";
    }
}