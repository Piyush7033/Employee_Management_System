package EmployeeManagementSystem.repository;

import EmployeeManagementSystem.dto.LoginRequest;
import EmployeeManagementSystem.entity.RegisterEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterEmployeeRepository extends JpaRepository<RegisterEmployee,Integer> {
    RegisterEmployee findByUserId(String userId);
    Optional<RegisterEmployee> findFirstByOrderByIdDesc();
    Optional<RegisterEmployee> findByEmail(String email);
}
