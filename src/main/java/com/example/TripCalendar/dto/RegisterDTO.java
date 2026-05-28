package com.example.TripCalendar.dto;

import com.example.TripCalendar.entity.UserEntity;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class RegisterDTO {

    private String username;
    private String password;

    public UserEntity toEntity() {
        return new UserEntity(null, username, password);
    }
}
