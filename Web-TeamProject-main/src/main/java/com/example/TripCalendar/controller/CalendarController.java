package com.example.TripCalendar.controller;

import com.example.TripCalendar.dto.CalendarTripDTO;
import com.example.TripCalendar.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CalendarController {

    @GetMapping("/")
    public String calendar(HttpSession session, Model model) {
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("loginUser", loginUser);

        return "calendar";
    }


    @GetMapping("/api/trips/calendar")
    @ResponseBody
    public List<CalendarTripDTO> calendarApi(HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
        if (loginUser == null) {
            return List.of();
        }
        return List.of();
    }
}
