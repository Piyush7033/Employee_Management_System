package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.entity.Department;
import EmployeeManagementSystem.service.DepartmentService;
import EmployeeManagementSystem.service.EmployeeProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeProfileService employeeProfileService;

    @GetMapping("/add")
    public String showAddDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("employees", employeeProfileService.getAllProfiles());
        model.addAttribute("pageTitle", "Add Department");
        // Redirect to admin/employee-management/add-department (matches AdminController path)
        return "admin/employee-management/add-department";
    }

    @PostMapping("/save")
    public String saveDepartment(@Valid @ModelAttribute("department") Department department,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        // Check for duplicate department name
        if (departmentService.existsByDepartmentName(department.getDepartmentName())) {
            bindingResult.rejectValue("departmentName", "error.department", "Department name already exists");
        }

        // Check for duplicate department code
        if (department.getDepartmentCode() != null && !department.getDepartmentCode().isEmpty()) {
            if (departmentService.existsByDepartmentCode(department.getDepartmentCode())) {
                bindingResult.rejectValue("departmentCode", "error.department", "Department code already exists");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("employees", employeeProfileService.getAllProfiles());
            model.addAttribute("pageTitle", "Add Department");
            return "admin/employee-management/add-department";
        }

        try {
            Department savedDepartment = departmentService.saveDepartment(department);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Department '" + savedDepartment.getDepartmentName() + "' added successfully!");
            // Redirect to department list
            return "redirect:/admin/departments/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding department: " + e.getMessage());
            model.addAttribute("employees", employeeProfileService.getAllProfiles());
            model.addAttribute("pageTitle", "Add Department");
            return "admin/employee-management/add-department";
        }
    }

    @GetMapping("/list")
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("pageTitle", "Department List");
        return "admin/employee-management/department-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditDepartmentForm(@PathVariable Long id, Model model) {
        Department department = departmentService.getDepartmentById(id);
        model.addAttribute("department", department);
        model.addAttribute("employees", employeeProfileService.getAllProfiles());
        model.addAttribute("pageTitle", "Edit Department");
        return "admin/employee-management/add-department";
    }

    @PostMapping("/update/{id}")
    public String updateDepartment(@PathVariable Long id,
                                   @Valid @ModelAttribute("department") Department department,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("employees", employeeProfileService.getAllProfiles());
            model.addAttribute("pageTitle", "Edit Department");
            return "admin/employee-management/add-department";
        }

        try {
            departmentService.updateDepartment(id, department);
            redirectAttributes.addFlashAttribute("successMessage", "Department updated successfully!");
            return "redirect:/admin/departments/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating department: " + e.getMessage());
            model.addAttribute("employees", employeeProfileService.getAllProfiles());
            model.addAttribute("pageTitle", "Edit Department");
            return "admin/employee-management/add-department";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            departmentService.deleteDepartment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Department deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting department: " + e.getMessage());
        }
        return "redirect:/admin/departments/list";
    }

    // AJAX endpoint to get designations by department
    @GetMapping("/designations")
    @ResponseBody
    public List<String> getDesignations(@RequestParam String departmentName) {
        return departmentService.getDesignationsByDepartment(departmentName);
    }

    // Redirect from department to employee form
    @GetMapping("/add-employee/{departmentId}")
    public String redirectToAddEmployee(@PathVariable Long departmentId, RedirectAttributes redirectAttributes) {
        Department department = departmentService.getDepartmentById(departmentId);
        redirectAttributes.addAttribute("department", department.getDepartmentName());
        return "redirect:/admin/employees/add";
    }
}