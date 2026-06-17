package EmployeeManagementSystem.repository;

//import com.ems.entity.Department;
import EmployeeManagementSystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByDepartmentNameContainingIgnoreCase(String departmentName);

}