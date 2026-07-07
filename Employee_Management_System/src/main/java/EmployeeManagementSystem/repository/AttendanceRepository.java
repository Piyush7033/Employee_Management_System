package EmployeeManagementSystem.repository;

//import com.ems.entity.Attendance;
//import com.ems.entity.Employee;
import EmployeeManagementSystem.entity.Attendance;
import EmployeeManagementSystem.entity.Employee;
import EmployeeManagementSystem.enums.WorkMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployee(
            Employee employee);

    List<Attendance> findByDate(
            LocalDate date);

    Attendance findByEmployeeAndDate(
            Employee employee,
            LocalDate date);
    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);
    List<Attendance> findByAttendanceDateAndWorkMode(LocalDate attendanceDate, WorkMode workMode);
    List<Attendance> findByEmployeeId(String employeeId);
}