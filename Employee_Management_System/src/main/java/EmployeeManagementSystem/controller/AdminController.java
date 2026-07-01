package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.dto.DashboardStatsDTO;
import EmployeeManagementSystem.entity.Activity;
import EmployeeManagementSystem.entity.Employee;
import EmployeeManagementSystem.service.ActivityService;
import EmployeeManagementSystem.service.DashboardService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
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

                            // Activity Pagination
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,

                            // Birthday Pagination
                            @RequestParam(defaultValue = "0") int birthdayPage,

                            // Anniversary Pagination
                            @RequestParam(defaultValue = "0") int anniversaryPage) {

        // ================= DASHBOARD STATS =================
        DashboardStatsDTO stats = dashboardService.getDashboardStats();

        model.addAttribute("stats", stats);

        // ================= CARDS =================
        model.addAttribute("totalEmployees", stats.getTotalEmployees());
        model.addAttribute("departments", stats.getDepartments());
        model.addAttribute("attendanceRate", stats.getAttendanceRate());
        model.addAttribute("payroll", stats.getPayroll());

        // ================= CHART DATA =================
        model.addAttribute("joined", stats.getJoined());
        model.addAttribute("left", stats.getLeft());
        model.addAttribute("attendanceTrend", stats.getAttendanceTrend());

        // ================= KPI =================
        int newJoinees = stats.getJoined() != null
                ? stats.getJoined().stream().mapToInt(Integer::intValue).sum()
                : 0;

        model.addAttribute("newJoinees", newJoinees);
        model.addAttribute("vacancies", 18);
        model.addAttribute("pendingLeaves", 11);

        // ================= UPCOMING BIRTHDAYS =================
        Page<Employee> birthdayEmployees =
                dashboardService.getUpcomingBirthdays(birthdayPage, 5);

        model.addAttribute("birthdays",
                birthdayEmployees.getContent());

        model.addAttribute("birthdayCurrentPage",
                birthdayPage);

        model.addAttribute("birthdayTotalPages",
                birthdayEmployees.getTotalPages());

        // ================= WORK ANNIVERSARIES =================
        Page<Employee> anniversaryEmployees =
                dashboardService.getUpcomingAnniversaries(anniversaryPage, 5);

        model.addAttribute("anniversaries",
                anniversaryEmployees.getContent());

        model.addAttribute("anniversaryCurrentPage",
                anniversaryPage);

        model.addAttribute("anniversaryTotalPages",
                anniversaryEmployees.getTotalPages());

        // ================= RECENT ACTIVITIES =================
        Page<Activity> activityPage =
                activityService.getActivities(page, size);

        model.addAttribute("activities",
                activityPage.getContent());

        model.addAttribute("currentPage",
                page);

        model.addAttribute("totalPages",
                activityPage.getTotalPages());

        model.addAttribute("hasNext",
                activityPage.hasNext());

        model.addAttribute("hasPrevious",
                activityPage.hasPrevious());

        return "admin-dashboard";
    }
}