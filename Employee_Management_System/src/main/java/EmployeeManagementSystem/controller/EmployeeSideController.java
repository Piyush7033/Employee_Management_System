package EmployeeManagementSystem.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeSideController {

    @GetMapping("/dashboard")
    public String dashboard(){
        return "employeeside-dashboard";
    }

    @GetMapping("/profile")
    public String profile(){
        return "employee/profile";
    }
}
