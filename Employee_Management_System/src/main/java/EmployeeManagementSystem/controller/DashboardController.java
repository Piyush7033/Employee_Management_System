package EmployeeManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDashboard(jakarta.servlet.http.HttpServletRequest request, org.springframework.ui.Model model) {

        boolean isLoggedIn = false;

        // Check karenge ki kya user ke paas valid login cookie hai?
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                    isLoggedIn = true;
                    break;
                }
            }
        }

        // Is boolean flag ko hum login/logout button handle karne ke liye use karenge
        model.addAttribute("isUserLoggedIn", isLoggedIn);

        return "dashboard"; // Aapka HTML page return hoga
    }

}