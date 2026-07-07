package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.entity.EmployeeProfile;
import EmployeeManagementSystem.entity.RegisterEmployee;
import EmployeeManagementSystem.jwt.JwtUtil;
import EmployeeManagementSystem.repository.EmployeeProfileRepository;
import EmployeeManagementSystem.service.RegisterEmployeeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class SidebarGlobalAdvice {
    private final JwtUtil jwtUtil;
    private final RegisterEmployeeService employeeService;
    private final EmployeeProfileRepository employeeProfileRepository;

    @ModelAttribute
    public void addSidebarData(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                    try {
                        String employeeId = jwtUtil.extractUsername(cookie.getValue());
                        RegisterEmployee emp = employeeService.getEmployeeById(employeeId);

                        if (emp != null) {
                            // 1. Name aur ID set karein
                            model.addAttribute("loggedInEmpName", emp.getName());
                            model.addAttribute("loggedInEmpId", emp.getId());

                            // 2. Photo set karein (Sahi String ID se)
                            EmployeeProfile empProfile = employeeProfileRepository.findByUserId(employeeId).orElse(null);
                            model.addAttribute("loggedInEmpPhoto", empProfile != null ? empProfile.getPhoto() : null);
                        }
                    } catch (Exception e) {
                        // Token expire ya invalid hone par handle karein
                    }
                    break;
                }
            }
        }

}
}
