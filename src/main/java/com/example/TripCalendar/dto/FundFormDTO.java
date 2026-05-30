package com.example.TripCalendar.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class FundFormDTO {
    private int amount;
    private LocalDate paidDate;
    private String note;
}