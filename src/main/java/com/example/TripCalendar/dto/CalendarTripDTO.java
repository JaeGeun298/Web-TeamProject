package com.example.TripCalendar.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarTripDTO {

    private Long id;
    private String title;
    private String start;
    private String end;
}
