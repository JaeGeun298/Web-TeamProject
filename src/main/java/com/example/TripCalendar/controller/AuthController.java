package com.example.TripCalendar.controller;

import com.example.TripCalendar.dto.LoginDTO;
import com.example.TripCalendar.dto.RegisterDTO;
import com.example.TripCalendar.entity.UserEntity;
import com.example.TripCalendar.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @GetMapping("/auth/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/auth/register")
    public String register(RegisterDTO form, RedirectAttributes rttr) {
        UserEntity user = userService.register(form);

        if (user == null) {
            rttr.addFlashAttribute("msg", "이미 사용 중인 아이디입니다.");
            return "redirect:/auth/register";
        }

        rttr.addFlashAttribute("msg", "회원가입이 완료되었습니다. 로그인해주세요.");
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/auth/login")
    public String login(LoginDTO form, HttpSession session, RedirectAttributes rttr) {
        UserEntity user = userService.login(form);

        if (user == null) {
            rttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "redirect:/auth/login";
        }

        session.setAttribute("loginUser", user);
        return "redirect:/";
    }

    @PostMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        log.info("로그아웃");
        return "redirect:/auth/login";
    }
}
