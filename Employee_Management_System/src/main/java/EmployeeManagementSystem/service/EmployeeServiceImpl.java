package EmployeeManagementSystem.service;

import EmployeeManagementSystem.entity.Employee;
import EmployeeManagementSystem.entity.Salary;
import EmployeeManagementSystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // ================= SAVE =================
    @Override
    public Employee saveEmployee(Employee employee) {

        // link salary safely (INSERT case only)
        if (employee.getSalaryDetails() != null) {
            employee.getSalaryDetails().setEmployee(employee);
        }

        // link attendance safely
        if (employee.getAttendanceList() != null) {
            employee.getAttendanceList()
                    .forEach(a -> a.setEmployee(employee));
        }

        return employeeRepository.save(employee);
    }

    // ================= UPDATE (FIXED LOGIC) =================
    @Override
    public Employee updateEmployee(Long id, Employee employee) {

        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // basic fields update
        existing.setName(employee.getName());
        existing.setEmail(employee.getEmail());
        existing.setPhone(employee.getPhone());
        existing.setJoiningDate(employee.getJoiningDate());
        existing.setDepartment(employee.getDepartment());

        // ================= SALARY FIX (IMPORTANT) =================
        if (employee.getSalaryDetails() != null) {

            Salary newSalary = employee.getSalaryDetails();

            if (existing.getSalaryDetails() != null) {

                // 🔥 UPDATE EXISTING SALARY (NO INSERT → FIX DUPLICATE ERROR)
                Salary existingSalary = existing.getSalaryDetails();

                existingSalary.setBasicSalary(newSalary.getBasicSalary());
                existingSalary.setBonus(newSalary.getBonus());
                existingSalary.setDeduction(newSalary.getDeduction());

            } else {

                // 🔥 FIRST TIME SALARY INSERT
                newSalary.setEmployee(existing);
                existing.setSalaryDetails(newSalary);
            }
        }

        // ================= ATTENDANCE UPDATE =================
        if (employee.getAttendanceList() != null) {

            existing.getAttendanceList().clear();

            employee.getAttendanceList().forEach(a -> {
                a.setEmployee(existing);
                existing.getAttendanceList().add(a);
            });
        }

        return employeeRepository.save(existing);
    }

    // ================= DELETE =================
    @Override
    public void deleteEmployee(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        employeeRepository.delete(emp);
    }

    // ================= GET BY ID =================
    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    // ================= LIST =================
    @Override
    public List<Employee> getAllEmployeesList() {
        return employeeRepository.findAll();
    }

    // ================= PAGINATION =================
    @Override
    public Page<Employee> getAllEmployees(int pageNo, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by(sortBy == null || sortBy.isBlank() ? "id" : sortBy).ascending()
        );

        return employeeRepository.findAll(pageable);
    }

    // ================= SEARCH =================
    @Override
    public Page<Employee> searchEmployee(String keyword, Pageable pageable) {
        return employeeRepository.searchAll(keyword, pageable);
    }
}