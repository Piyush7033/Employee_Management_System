package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeSideController {

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model){
        boolean isLoggedIn=false;
        Cookie[] cookies=request.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies){
                if ("jwtToken".equals(cookie.getName()) && cookie.getValue()!=null && !cookie.getValue().isEmpty()){
                    isLoggedIn=true;
                    break;
                }
            }
        }
        model.addAttribute("isUserLoggedIn",isLoggedIn);

        return "employeeside-dashboard";

    }

    @GetMapping("/profile")
    public String profile(){
        return "employee/profile";
    }

}
