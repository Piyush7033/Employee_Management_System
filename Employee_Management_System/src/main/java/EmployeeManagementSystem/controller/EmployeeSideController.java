package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.jwt.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeSideController {
    private final JwtUtil jwtUtil;
    @GetMapping("/dashboard")
    public String dashboard() {
        return "employeeside-dashboard";
    }

    @GetMapping("/profile")
    public String profile(){
        return "employee/profile";
    }

}
