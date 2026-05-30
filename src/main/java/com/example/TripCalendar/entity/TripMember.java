package com.example.TripCalendar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trip_members")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TripMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
