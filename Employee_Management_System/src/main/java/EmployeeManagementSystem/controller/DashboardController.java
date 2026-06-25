package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final JwtUtil jwtUtil;

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

//    @GetMapping("/dashboard")
//    public String showDashboard(HttpServletRequest request, Model model) {
//
//        boolean isLoggedIn = false;
//        boolean isManager=false;
//        String token=null;
//        if (request.getCookies() != null) {
//            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
//                if ("jwtToken".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
//                    isLoggedIn = true;
//                    token=cookie.getValue();
//                    break;
//                }
//            }
//        }
//        if (isLoggedIn && token != null) {
//            try {
//                isManager = true;
//            } catch (Exception e) {
//                isLoggedIn = false;
//            }
//        }
//        model.addAttribute("isUserLoggedIn", isLoggedIn);
//        model.addAttribute("isManager",isManager);
//
//        return "redirect:/login";
//    }
    @GetMapping("/manager-dashboard")
    public String managerDashboard() {
        return "manager-dashboard";
    }
    @GetMapping("/admin-dashboard")
    public String adminDashboard(){
        return "admin-dashboard";
        }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "403"; // maps to templates/403.html
    }

}