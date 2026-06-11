package EmployeeManagementSystem.repository;

import EmployeeManagementSystem.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    // ✅ Correct JPA derived query (based on relation)
    Optional<Salary> findByEmployee_Id(Long employeeId);
}