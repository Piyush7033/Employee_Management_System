package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.dto.LoginRequest;
import EmployeeManagementSystem.entity.AttendanceTracking;
import EmployeeManagementSystem.entity.Employee;
import EmployeeManagementSystem.entity.RegisterEmployee;
import EmployeeManagementSystem.jwt.JwtUtil;
import EmployeeManagementSystem.repository.AttendanceTrackingRepository;
import EmployeeManagementSystem.service.AttendanceTrackingService;
import EmployeeManagementSystem.service.RegisterEmployeeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
    private final RegisterEmployeeService service;
    private final AttendanceTrackingService attendanceTrackingService;
    private final AttendanceTrackingRepository attendanceTrackingRepository;


    private  final JwtUtil jwtUtil;
    @GetMapping("/employeeRegisterForm")
    public String openForm(Model model){
        model.addAttribute("registerEmployee",new RegisterEmployee());
        return "register";
    }
    @PostMapping("/register")
    public String createUser(@ModelAttribute RegisterEmployee registerEmployee,Model model)
    {
         String generatedPassword=service.registerEmployee(registerEmployee);
        model.addAttribute("message", "Employee Registered Successfully!");
        model.addAttribute("generatedId", registerEmployee.getUserId());
        model.addAttribute("generatedPassword", generatedPassword);

        return "register-success";
    }
    @GetMapping("/loginPage")
    public String openLoginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }
//    @PostMapping("/login")
//    public String login(@ModelAttribute("loginRequest") LoginRequest request,
//                        HttpServletResponse response, Model model) {
//        try {
//
//            String token = service.login(request);
//
//            Cookie jwtCookie = new Cookie("jwtToken", token);
//            jwtCookie.setHttpOnly(true);
//            jwtCookie.setPath("/");
//            jwtCookie.setMaxAge(60 * 60);
//
//            response.addCookie(jwtCookie);
//            String role = jwtUtil.extractRole(token);
//            System.out.println("ROLE = " + role);
//
//            if ("ROLE_EMPLOYEE".equalsIgnoreCase(role)) {
//                return "redirect:/employee/dashboard";
//            }
//
//            return "redirect:/dashboard";
//
//        } catch (Exception e) {
//
//            model.addAttribute("error",
//                    "Invalid Credentials: " + e.getMessage());
//            model.addAttribute("loginRequest",new LoginRequest());
//
//            return "login";
//        }
//    }
//    @GetMapping("/forgot-password")
//    public String showForgotPasswordForm() {
//        return "forgot-password"; // forgot-password.html khulega
//    }
//
//    @PostMapping("/forgot-password")
//    public String processForgotPassword(@RequestParam("email") String email, Model model) {
//        try {
//            service.sendOtpProcessing(email);
//            model.addAttribute("email", email);
//            return "reset-password"; // OTP aur naya password daalne wale page par bhej diya
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//            return "forgot-password";
//        }
//    }


    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") LoginRequest request,
                        HttpServletRequest httpRequest,
                        HttpServletResponse response,
                        Model model) {
        try {

            String token = service.login(request);

            Cookie jwtCookie = new Cookie("jwtToken", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60);

            response.addCookie(jwtCookie);

            String role = jwtUtil.extractRole(token);

            System.out.println("ROLE = " + role);

            HttpSession session = httpRequest.getSession();

            Long employeeId = 1L;
            String employeeName = "Employee";

            session.setAttribute("employeeId", employeeId);
            session.setAttribute("employeeName", employeeName);

            AttendanceTracking attendance = new AttendanceTracking();
            attendance.setEmployeeId(employeeId);
            attendance.setEmployeeName(employeeName);
            attendance.setDate(LocalDate.now());
            attendance.setLoginTime(LocalDateTime.now());
            attendance.setStatus("Present");

            attendanceTrackingRepository.save(attendance);

            System.out.println("Attendance Saved Successfully");

            if ("ROLE_EMPLOYEE".equalsIgnoreCase(role)) {
                return "redirect:/employee/dashboard";
            }

            return "redirect:/dashboard";

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Invalid Credentials: " + e.getMessage());

            model.addAttribute(
                    "loginRequest",
                    new LoginRequest());

            return "login";
        }

    }







    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("email") String email,
                                       @RequestParam("otp") String otp,
                                       @RequestParam("newPassword") String newPassword,
                                       Model model) {
        try {
            service.verifyOtpAndChangePassword(email, otp, newPassword);
            model.addAttribute("message", "Password changed successfully! Please login.");
            model.addAttribute("loginRequest", new LoginRequest());
            return "login"; // Password change hote hi wapas login screen par bhej diya
        } catch (Exception e) {
            model.addAttribute("email", email); // Taaki page reload par email na khoye
            model.addAttribute("error", e.getMessage());

            return "reset-password";
        }
    }
    @GetMapping("/logout")
    public String logout(jakarta.servlet.http.HttpServletRequest request,
                         jakarta.servlet.http.HttpServletResponse response) {

        // 1. Purani cookie ko dhoondo aur use khatam karo
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName())) {
                    cookie.setValue("");       // Value khali kar di
                    cookie.setPath("/");       // Same path hona zaroori hai
                    cookie.setMaxAge(0);       // MaxAge 0 karne se cookie turant delete ho jaati hai
                    cookie.setHttpOnly(true);

                    response.addCookie(cookie); // Browser ko wapas bhej diya delete karne ke liye
                    break;
                }
            }
        }

        // 2. Logout ke baad user ko login page par bhej dein ek success message ke sath
        return "redirect:/auth/loginPage?logout=true";
    }
}
