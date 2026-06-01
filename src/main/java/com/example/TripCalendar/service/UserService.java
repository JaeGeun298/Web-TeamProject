package com.example.TripCalendar.service;

import com.example.TripCalendar.dto.LoginDTO;
import com.example.TripCalendar.dto.RegisterDTO;
import com.example.TripCalendar.entity.UserEntity;

public interface UserService {

    UserEntity register(RegisterDTO form);
    UserEntity login(LoginDTO form);
    boolean updatePassword(Long userId, String currentPassword, String newPassword);
    void deleteUser(Long userId);
}
