package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.dto.DashboardStatsDTO;
import EmployeeManagementSystem.entity.Activity;
import EmployeeManagementSystem.service.ActivityService;
import EmployeeManagementSystem.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final ActivityService activityService;
    private final DashboardService dashboardService;

    public AdminController(ActivityService activityService,
                           DashboardService dashboardService) {
        this.activityService = activityService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {

        DashboardStatsDTO stats = dashboardService.getDashboardStats();

        model.addAttribute("stats", stats);

        model.addAttribute("totalEmployees", stats.getTotalEmployees());
        model.addAttribute("departments", stats.getDepartments());
        model.addAttribute("attendanceRate", stats.getAttendanceRate());
        model.addAttribute("payroll", stats.getPayroll());

        model.addAttribute("joined", stats.getJoined());
        model.addAttribute("left", stats.getLeft());
        model.addAttribute("attendanceTrend", stats.getAttendanceTrend());

        int newJoinees = (stats.getJoined() != null)
                ? stats.getJoined().stream().mapToInt(Integer::intValue).sum()
                : 0;

        model.addAttribute("newJoinees", newJoinees);

        model.addAttribute("vacancies", 18);
        model.addAttribute("pendingLeaves", 11);

        List<?> birthdays = dashboardService.getUpcomingBirthdaysDTO();

        model.addAttribute("birthdays",
                birthdays != null ? birthdays : Collections.emptyList());

        List<?> anniversaries = dashboardService.getUpcomingAnniversariesDTO();
        model.addAttribute("anniversaries",
                anniversaries != null ? anniversaries : Collections.emptyList());

        log.info("Birthdays count: {}", birthdays != null ? birthdays.size() : 0);
        log.info("Anniversaries count: {}", anniversaries != null ? anniversaries.size() : 0);

        Page<Activity> activityPage = activityService.getActivities(page, size);

        model.addAttribute("activities", activityPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", activityPage.getTotalPages());
        model.addAttribute("hasNext", activityPage.hasNext());
        model.addAttribute("hasPrevious", activityPage.hasPrevious());

        return "admin-dashboard";
    }
}