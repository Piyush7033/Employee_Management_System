package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.entity.Employee;
import EmployeeManagementSystem.entity.Salary;
import EmployeeManagementSystem.service.EmployeeService;
import EmployeeManagementSystem.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private EmployeeService employeeService;

    // ================= MAIN PAGE =================
    @GetMapping
    public String salaryHome(Model model) {

        model.addAttribute("employees",
                employeeService.getAllEmployeesList());

        model.addAttribute("salaries",
                salaryService.getAllSalaries());

        return "salary-list";
    }

    // ================= SAVE / UPDATE (FINAL CLEAN LOGIC) =================
    @PostMapping("/save")
    public String saveSalary(@ModelAttribute Salary salary,
                             @RequestParam Long employeeId) {

        Employee employee = employeeService.getEmployeeById(employeeId);

        if (employee == null) {
            return "redirect:/employees";
        }

        // attach employee
        salary.setEmployee(employee);

        // 🔥 NO MANUAL ID HANDLING REQUIRED
        salaryService.saveSalary(salary);

        return "redirect:/salary";
    }

    // ================= VIEW =================
    @GetMapping("/{employeeId}")
    public String viewSalary(@PathVariable Long employeeId, Model model) {

        Salary salary = salaryService.getSalaryByEmployeeId(employeeId);

        if (salary == null) {
            return "redirect:/salary";
        }

        double netSalary =
                (salary.getBasicSalary() != null ? salary.getBasicSalary() : 0) +
                        (salary.getBonus() != null ? salary.getBonus() : 0) -
                        (salary.getDeduction() != null ? salary.getDeduction() : 0);

        model.addAttribute("salary", salary);
        model.addAttribute("netSalary", netSalary);

        return "salary-view";
    }

    // ================= DELETE =================
    @GetMapping("/delete/{employeeId}")
    public String deleteSalary(@PathVariable Long employeeId) {

        Salary salary = salaryService.getSalaryByEmployeeId(employeeId);

        if (salary != null) {
            salaryService.deleteSalary(salary.getId());
        }

        return "redirect:/salary";
    }
}