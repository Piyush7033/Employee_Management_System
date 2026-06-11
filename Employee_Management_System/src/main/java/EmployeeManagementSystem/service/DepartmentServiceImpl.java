package EmployeeManagementSystem.service;

import EmployeeManagementSystem.entity.Department;
import EmployeeManagementSystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Long id, Department department) {

        Department existing = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Department not found with id: " + id));

        existing.setDepartmentName(department.getDepartmentName());
        existing.setDescription(department.getDescription());

        return departmentRepository.save(existing);
    }

    @Override
    public void deleteDepartment(Long id) {

        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }

        departmentRepository.deleteById(id);
    }

    @Override
    public Department getDepartmentById(Long id) {

        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Department not found with id: " + id));
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}