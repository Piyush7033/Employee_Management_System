package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.dto.SalarySlipDto;
import EmployeeManagementSystem.entity.Employee;
import EmployeeManagementSystem.entity.Salary;
import EmployeeManagementSystem.service.EmployeeService;
import EmployeeManagementSystem.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salary")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;
    private final EmployeeService employeeService;

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


        salary.setEmployee(employee);

        salaryService.saveSalary(salary);

        return "redirect:/salary";
    }


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

    @GetMapping("/delete/{employeeId}")
    public String deleteSalary(@PathVariable Long employeeId) {

        Salary salary = salaryService.getSalaryByEmployeeId(employeeId);

        if (salary != null) {
            salaryService.deleteSalary(salary.getId());
        }
        return "redirect:/salary";
    }

    @GetMapping("/slip/{id}")
    public String getSalarySlip(@PathVariable Long id,Model model) {
        SalarySlipDto slipDto=salaryService.getSalarySlipById(id);
        model.addAttribute("slip",slipDto);
        return "salary-slip";
    }
}