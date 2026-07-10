package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.entity.Salary;
import EmployeeManagementSystem.service.SalaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("salaryList",
                salaryService.getAllSalaries());

        model.addAttribute("totalEmployees",
                salaryService.getTotalEmployees());

        model.addAttribute("paidCount",
                salaryService.getPaidEmployeesCount());

        model.addAttribute("pendingCount",
                salaryService.getPendingEmployeesCount());

        model.addAttribute("totalPayroll",
                salaryService.getTotalPayrollCost());

        model.addAttribute("paidSalary",
                salaryService.getTotalPaidSalary());

        model.addAttribute("pendingSalary",
                salaryService.getTotalPendingSalary());

        return "salary-dashboard";
    }

    @GetMapping("/add")
    public String addSalaryForm(Model model) {

        model.addAttribute("salary", new Salary());

        return "salary-form";
    }

    @PostMapping("/save")
    public String saveSalary(@ModelAttribute Salary salary) {

        salaryService.saveSalary(salary);

        return "redirect:/salary/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editSalary(@PathVariable Long id,
                             Model model) {

        model.addAttribute("salary",
                salaryService.getSalaryById(id));

        return "salary-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteSalary(@PathVariable Long id) {

        salaryService.deleteSalary(id);

        return "redirect:/salary/dashboard";
    }

    @GetMapping("/slip/{id}")
    public String salarySlip(@PathVariable Long id,
                             Model model) {

        model.addAttribute("salarySlip",
                salaryService.getSalarySlipById(id));

        return "salary-slip";
    }
}