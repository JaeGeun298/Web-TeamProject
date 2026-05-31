package com.example.TripCalendar.controller;

import com.example.TripCalendar.dto.CalendarTripDTO;
import com.example.TripCalendar.entity.Trip;
import com.example.TripCalendar.entity.UserEntity;
import com.example.TripCalendar.repository.TripRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final TripRepository tripRepository;

    @GetMapping("/")
    public String calendar(HttpSession session, Model model) {
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("loginUser", loginUser);

        List<Trip> trips = tripRepository.findByTripMembers_User(loginUser);
        if (!trips.isEmpty()) {
            model.addAttribute("tripId", trips.get(0).getId());
        }

        return "calendar";
    }


    @GetMapping("/api/trips/calendar")
    @ResponseBody
    public List<CalendarTripDTO> calendarApi(HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");

        if (loginUser == null) {
            return List.of();
        }

        List<Trip> trips = tripRepository.findByTripMembers_User(loginUser);

        return trips.stream()
                .filter(t -> t.getStartDate() != null && t.getEndDate() != null)
                .map(t -> new CalendarTripDTO(
                        t.getId(),
                        t.getTitle(),
                        t.getStartDate().toString(),
                        t.getEndDate().toString()))
                .collect(Collectors.toList());
    }
}
