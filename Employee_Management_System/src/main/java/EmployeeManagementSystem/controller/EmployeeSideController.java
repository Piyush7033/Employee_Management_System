package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.dto.AnniversaryDTO;
import EmployeeManagementSystem.dto.BirthdayDTO;
import EmployeeManagementSystem.entity.Policy;
import EmployeeManagementSystem.entity.RegisterEmployee;
import EmployeeManagementSystem.entity.WfhRequest;
import EmployeeManagementSystem.jwt.JwtUtil;
import EmployeeManagementSystem.repository.LeaveRepository;
import EmployeeManagementSystem.repository.WfhRequestRepository;
import EmployeeManagementSystem.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeSideController {
    private final JwtUtil jwtUtil;
    private final RegisterEmployeeService service;
    private final EmployeeService employeeService;
    private final PolicyService policyService;
    private final AttendanceService attendanceService;
    private final ProjectOffService projectOffService;
    private final WfhService wfhService;
    private final LeaveService leaveService;
    private final WfhRequestRepository wfhRequestRepository;
    private final LeaveRepository leaveRepository;
    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        boolean isLoggedIn = false;
        String employeeName = "DASHBOARD";
        Long loggedInEmpId = null;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                    isLoggedIn = true;
                    String employeeId = jwtUtil.extractUsername(cookie.getValue());
                    RegisterEmployee emp = service.getEmployeeById(employeeId);

                    if (emp != null) {
                        employeeName = emp.getName();
                        loggedInEmpId = Long.valueOf(emp.getId());
                    }
                    break;
                }
            }
        }

        List<BirthdayDTO> birthdayEmployee = employeeService.getUpcomingBirthdays();
        List<AnniversaryDTO> upcomingAnniversaries = employeeService.getUpcomingAnniversaries();

        model.addAttribute("isUserLoggedIn", isLoggedIn);
        model.addAttribute("loggedInEmpName", employeeName);
        model.addAttribute("birthdayList", birthdayEmployee);
        model.addAttribute("anniversaryList", upcomingAnniversaries);
        model.addAttribute("attendance", attendanceService.getTodayAttendance());
        model.addAttribute("wfhList", attendanceService.getTodayWFHEmployees());
        model.addAttribute("wfhList",wfhService.getWFHEmployees());
        model.addAttribute("leaveList",leaveService.getAllLeaveRequest());
        model.addAttribute("totalWFH",wfhRequestRepository.countByStatus("APPROVED"));
        model.addAttribute("totalLeaves",leaveRepository.countByStatus("APPROVED"));


        model.addAttribute("projectOffLogs", projectOffService.getTodayProjectOffLogs());

        model.addAttribute("loggedInEmpId", loggedInEmpId);

        return "employeeside-dashboard";
    }

    @GetMapping("/profile")
    public String profile(){
        return "employee/profile";
    }

    @GetMapping("/wfh/apply")
    public String showWfhForm(Model model) {
        model.addAttribute("currentPage", "wfh");
        model.addAttribute("wfhRequest", new WfhRequest());
        return "wfh-apply-page";
    }
    @PostMapping("/wfh/save")
    public String saveWfhRequest(@ModelAttribute("wfhRequest") WfhRequest request, @AuthenticationPrincipal UserDetails currentUser){
        if (currentUser!=null){
            request.setEmployeeId(currentUser.getUsername());
            request.setEmployeeName(currentUser.getUsername());
        }
        wfhService.saveRequest(request);
        return "redirect:/employee/wfh/apply?success=true";
    }

    @GetMapping("/policy")
    public String viewPolicy(Model model){
        List<Policy> policies=policyService.getAllPolicy();
        model.addAttribute("policies",policies);
        return "policies";
    }

}
