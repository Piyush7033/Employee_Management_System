package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.entity.Department;
import EmployeeManagementSystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public String getAllDepartments(
            Model model) {

        model.addAttribute(
                "departments",
                departmentService
                        .getAllDepartments());

        return "department-list";
    }

    @GetMapping("/add")
    public String addDepartmentForm(
            Model model) {

        model.addAttribute(
                "department",
                new Department());

        return "department-form";
    }

    @PostMapping("/save")
    public String saveDepartment(
            @ModelAttribute Department department) {

        departmentService.saveDepartment(
                department);

        return "redirect:/departments";
    }

    @GetMapping("/edit/{id}")
    public String editDepartment(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "department",
                departmentService
                        .getDepartmentById(id));

        return "department-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return "redirect:/departments";
    }
}