package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.entity.Employee;
import EmployeeManagementSystem.entity.RegisterEmployee;
import EmployeeManagementSystem.jwt.JwtUtil;
import EmployeeManagementSystem.service.EmployeeService;
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
    private final EmployeeService employeeService;
    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        boolean isLoggedIn = false;
        String employeeName = "DASHBOARD";
        Long loggedInEmpId = null; // ID store karne ke liye naya variable banaya (Agar aapki ID String hai to String rakhein)

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                    isLoggedIn = true;
                    String employeeId = jwtUtil.extractUsername(cookie.getValue());
                    RegisterEmployee emp = service.getEmployeeById(employeeId);

                    if (emp != null) {
                        employeeName = emp.getName();
                        loggedInEmpId = Long.valueOf(emp.getId()); // ✨ YAHAAN SE SAHI ID MILI!
                    }
                    break;
                }
            }
        }

        List<Employee> birthdayEmployee = employeeService.getUpcomingBirthdays();
        List<Employee> upcomingAnniversaries = employeeService.getUpcomingAnniversaries();

        model.addAttribute("isUserLoggedIn", isLoggedIn);
        model.addAttribute("loggedInEmpName", employeeName);
        model.addAttribute("birthdays", birthdayEmployee); // HTML ke hisab se name 'birthdays' rakha
        model.addAttribute("anniversaries", upcomingAnniversaries); // HTML ke hisab se name 'anniversaries' rakha

        // ✨ HTML ke variable name (loggedInEmpId) se match karta hua attribute bheja
        model.addAttribute("loggedInEmpId", loggedInEmpId);

        return "employeeside-dashboard";
    }

    @GetMapping("/profile")
    public String profile(){
        return "employee/profile";
    }

}
