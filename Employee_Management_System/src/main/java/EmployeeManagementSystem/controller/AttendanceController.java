package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.entity.Attendance;
import EmployeeManagementSystem.entity.Employee;
import EmployeeManagementSystem.service.AttendanceService;
import EmployeeManagementSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String getAllAttendance(Model model) {

        model.addAttribute(
                "attendanceList",
                attendanceService.getAllAttendance()
        );

        return "attendance-list";
    }

    @GetMapping("/add")
    public String showAttendanceForm(Model model) {

        model.addAttribute("attendance", new Attendance());

        // ✅ FIXED LINE
        List<Employee> employees = employeeService.getAllEmployeesList();

        model.addAttribute("employees", employees);

        System.out.println("Employees Found = " + employees.size());

        return "attendance-form";
    }

    @PostMapping("/save")
    public String saveAttendance(
            @ModelAttribute Attendance attendance,
            @RequestParam("employeeId") Long employeeId) {

        Employee employee = employeeService.getEmployeeById(employeeId);

        attendance.setEmployee(employee);

        attendanceService.saveAttendance(attendance);

        return "redirect:/attendance";
    }

    @GetMapping("/delete/{id}")
    public String deleteAttendance(@PathVariable Long id) {

        attendanceService.deleteAttendance(id);

        return "redirect:/attendance";
    }
}