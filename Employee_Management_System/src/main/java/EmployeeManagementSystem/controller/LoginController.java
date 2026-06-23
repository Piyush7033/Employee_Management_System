package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.dto.LoginRequest;
import EmployeeManagementSystem.entity.RegisterEmployee;
import EmployeeManagementSystem.service.RegisterEmployeeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
    private final RegisterEmployeeService service;
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
    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") LoginRequest request,
                        HttpServletResponse response, Model model) {
        try {

            String token = service.login(request);

            Cookie jwtCookie = new Cookie("jwtToken", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60); // 1 hour

            response.addCookie(jwtCookie);

            return "redirect:/dashboard";

        } catch (Exception e) {

            model.addAttribute("error", "Invalid Credentials: " + e.getMessage());
            model.addAttribute("loginRequest",new LoginRequest());

            return "login";
        }
    }
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        try {
            service.sendOtpProcessing(email);
            model.addAttribute("email", email);
            return "reset-password";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forgot-password";
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
            return "login";
        } catch (Exception e) {
            model.addAttribute("email", email);
            model.addAttribute("error", e.getMessage());

            return "reset-password";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {

        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    cookie.setHttpOnly(true);

                    response.addCookie(cookie);
                    break;
                }
            }
        }

        return "redirect:/auth/loginPage?logout=true";
    }
}
