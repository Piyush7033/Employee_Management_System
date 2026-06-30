package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.entity.Activity;
import EmployeeManagementSystem.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {

        model.addAttribute("joined", Arrays.asList(5, 8, 6, 10, 7, 12, 9));
        model.addAttribute("left", Arrays.asList(1, 2, 1, 3, 2, 2, 1));
        model.addAttribute("attendanceTrend", Arrays.asList(92, 95, 94, 96, 93, 97, 98));

        model.addAttribute("totalEmployees", 120);
        model.addAttribute("departments", 8);
        model.addAttribute("attendanceRate", 96);
        model.addAttribute("payroll", 850000);

        model.addAttribute("newJoinees", 5);
        model.addAttribute("vacancies", 18);
        model.addAttribute("pendingLeaves", 11);

        Page<Activity> activityPage = activityService.getActivities(page, size);

        model.addAttribute("activities", activityPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", activityPage.getTotalPages());

        return "admin-dashboard";
    }
}