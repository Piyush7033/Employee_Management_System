package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.jwt.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final JwtUtil jwtUtil;

    public AdminController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/dashboard")
    public String dashboard(@CookieValue(value = "token", required = false) String token) {

        String role = jwtUtil.extractRole(token);

        if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
            return "redirect:/admin/dashboard";
        }

        return "admin-dashboard";
    }

}