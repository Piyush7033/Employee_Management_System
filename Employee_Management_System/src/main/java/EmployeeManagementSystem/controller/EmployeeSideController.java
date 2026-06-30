package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.entity.RegisterEmployee;
import EmployeeManagementSystem.jwt.JwtUtil;
import EmployeeManagementSystem.service.RegisterEmployeeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeSideController {
    private final JwtUtil jwtUtil;
    private final RegisterEmployeeService service;
    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        boolean isLoggedIn = false;
        String employeeName = "DASHBOARD";
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                    isLoggedIn = true;
                     String employeeId = jwtUtil.extractUsername(cookie.getValue());
                        RegisterEmployee emp = service.getEmployeeById(employeeId);
                        if (emp!=null){
                            employeeName=emp.getName();
                        }
                        break;
                }
            }
        }
        List<RegisterEmployee> birthdayEmployee=service.getUpcomingBirthdays();

        model.addAttribute("isUserLoggedIn", isLoggedIn);
        model.addAttribute("loggedInEmpName", employeeName);
        model.addAttribute("birthdayList",birthdayEmployee);

        return "employeeside-dashboard";
    }

    @GetMapping("/profile")
    public String profile(){
        return "employee/profile";
    }

}
