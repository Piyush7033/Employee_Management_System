package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.jwt.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeSideController {

    private final JwtUtil jwtUtil;


    @GetMapping("/dashboard")
    public String dashboard(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isEmployee = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));

        if (isAdmin) {
            return "admin-dashboard";
        }

        if (isEmployee) {
            return "employeeside-dashboard";
        }

        return "redirect:/login";

    }

    @GetMapping("/profile")
    public String profile(){
        return "employee/profile";
    }

}
