package EmployeeManagementSystem.service;

//import com.ems.entity.Attendance;
import EmployeeManagementSystem.entity.Attendance;

import java.util.List;

public interface AttendanceService {

    Attendance saveAttendance(
            Attendance attendance);

    List<Attendance> getAllAttendance();

    Attendance getAttendanceById(
            Long id);

    void deleteAttendance(Long id);
}