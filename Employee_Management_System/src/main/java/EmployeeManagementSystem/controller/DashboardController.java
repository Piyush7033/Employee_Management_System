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
        boolean isManager=false;
        String token=null;
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                    isLoggedIn = true;
                    token=cookie.getValue();
                    break;
                }
            }
        }
        if (isLoggedIn && token != null) {
            try {
                // Replace this with your actual JWT role extracting logic, for example:
                // String role = jwtUtil.extractRole(token);
                // isManager = "MANAGER".equalsIgnoreCase(role) || "ROLE_MANAGER".equalsIgnoreCase(role);

                // For demonstration, let's assume a generic check.
                // Adjust this line to match how your application reads claims:
                isManager = true; // Set dynamically based on your JWT payload evaluation!
            } catch (Exception e) {
                // If token is expired or invalid
                isLoggedIn = false;
            }
        }
        model.addAttribute("isUserLoggedIn", isLoggedIn);
        model.addAttribute("isManager",isManager);

        return "dashboard";
    }
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "403"; // maps to templates/403.html
    }

}