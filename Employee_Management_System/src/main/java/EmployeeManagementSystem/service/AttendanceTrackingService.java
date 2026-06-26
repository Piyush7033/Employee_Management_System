package EmployeeManagementSystem.service;

import EmployeeManagementSystem.entity.AttendanceTracking;
import EmployeeManagementSystem.entity.Employee;

public interface AttendanceTrackingService {
    void markLogin (Employee employee);
    void markLogout (Long employeeId);

    AttendanceTracking getTodayAttendance(Long employeeId);
}
