package EmployeeManagementSystem.service;

import EmployeeManagementSystem.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    // ================= CRUD =================
    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);

    Employee getEmployeeById(Long id);

    // ================= PAGINATION + SORT =================
    Page<Employee> getAllEmployees(int pageNo, int pageSize, String sortBy);

    // ================= SEARCH =================
    Page<Employee> searchEmployee(String keyword, Pageable pageable);

    // ================= SIMPLE LIST (FOR DROPDOWNS) =================
    List<Employee> getAllEmployeesList();
}