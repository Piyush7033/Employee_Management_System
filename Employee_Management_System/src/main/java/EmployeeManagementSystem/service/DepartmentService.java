package EmployeeManagementSystem.service;

//import com.ems.entity.Department;
import EmployeeManagementSystem.entity.Department;

import java.util.List;

public interface DepartmentService {

    Department saveDepartment(
            Department department);

    Department updateDepartment(
            Long id,
            Department department);

    void deleteDepartment(Long id);

    Department getDepartmentById(
            Long id);

    List<Department> getAllDepartments();
}