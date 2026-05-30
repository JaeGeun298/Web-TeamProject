package com.example.TripCalendar.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class TripFormDTO {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
