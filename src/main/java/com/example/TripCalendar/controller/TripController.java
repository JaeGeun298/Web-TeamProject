package com.example.TripCalendar.controller;

import com.example.TripCalendar.dto.TripFormDTO;
import com.example.TripCalendar.entity.Trip;
import com.example.TripCalendar.entity.UserEntity;
import com.example.TripCalendar.repository.UserRepository;
import com.example.TripCalendar.service.TripService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;
    private final UserRepository userRepository;

    // 세션에서 로그인 유저 꺼내기
    private UserEntity getLoginUser(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("loginUser");
        if (user == null) throw new IllegalStateException("로그인이 필요합니다.");
        return user;
    }

    // 여행 목록
    @GetMapping
    public String list(HttpSession session, Model model) {
        UserEntity user = getLoginUser(session);
        model.addAttribute("trips", tripService.getMyTrips(user));
        return "trip/list";
    }

    // 여행 생성 페이지
    @GetMapping("/new")
    public String createForm(HttpSession session, Model model) {
        getLoginUser(session); // 로그인 체크
        model.addAttribute("tripForm", new TripFormDTO());
        return "trip/form";
    }

    // 여행 생성 처리
    @PostMapping
    public String create(@ModelAttribute TripFormDTO form, HttpSession session) {
        UserEntity user = getLoginUser(session);
        tripService.createTrip(form, user);
        return "redirect:/trips";
    }

    // 여행 대시보드
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model) {
        getLoginUser(session); // 로그인 체크
        Trip trip = tripService.getTrip(id);
        model.addAttribute("trip", trip);
        model.addAttribute("members", trip.getTripMembers());
        return "trip/detail";
    }

    // 여행 수정 페이지
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        getLoginUser(session);
        Trip trip = tripService.getTrip(id);
        TripFormDTO form = new TripFormDTO();
        form.setTitle(trip.getTitle());
        form.setStartDate(trip.getStartDate());
        form.setEndDate(trip.getEndDate());
        form.setDescription(trip.getDescription());
        model.addAttribute("tripForm", form);
        model.addAttribute("tripId", id);
        return "trip/form";
    }

    // 여행 수정 처리
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute TripFormDTO form, HttpSession session) {
        getLoginUser(session);
        tripService.updateTrip(id, form);
        return "redirect:/trips";
    }

    // 여행 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        getLoginUser(session);
        tripService.deleteTrip(id);
        return "redirect:/trips";
    }

    // 멤버 초대
    @PostMapping("/{id}/members")
    public String addMember(@PathVariable Long id, @RequestParam String username, HttpSession session) {
        getLoginUser(session);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다."));
        tripService.addMember(id, user);
        return "redirect:/trips/" + id;
    }

    // 멤버 탈퇴
    @PostMapping("/{id}/members/{memberId}/delete")
    public String removeMember(@PathVariable Long id, @PathVariable Long memberId, HttpSession session) {
        getLoginUser(session);
        tripService.removeMember(memberId);
        return "redirect:/trips/" + id;
    }
}