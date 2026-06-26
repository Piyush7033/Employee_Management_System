package EmployeeManagementSystem.repository;

import EmployeeManagementSystem.entity.AttendanceTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AttendanceTrackingRepository
        extends JpaRepository<AttendanceTracking, Long> {

    AttendanceTracking findTopByEmployeeIdAndDateOrderByIdDesc(
            Long employeeId,
            LocalDate date
    );
}