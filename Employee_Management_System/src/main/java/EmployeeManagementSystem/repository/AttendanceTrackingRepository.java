package EmployeeManagementSystem.repository;

import EmployeeManagementSystem.entity.AttendanceTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceTrackingRepository
        extends JpaRepository<AttendanceTracking, Long> {

    AttendanceTracking findTopByEmployeeIdAndDateOrderByIdDesc(
            Long employeeId,
            LocalDate date
    );

//    List<AttendanceTracking> findByEmployeeIdAndDateOrderByIdDesc(Long employeeId);

    List<AttendanceTracking> findByEmployeeIdOrderByDateDesc(Long employeeId);
}