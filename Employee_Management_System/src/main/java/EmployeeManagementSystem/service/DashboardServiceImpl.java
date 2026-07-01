package EmployeeManagementSystem.service;

import EmployeeManagementSystem.dto.DashboardStatsDTO;
import EmployeeManagementSystem.entity.Employee;
import EmployeeManagementSystem.repository.DepartmentRepository;
import EmployeeManagementSystem.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public DashboardServiceImpl(EmployeeRepository employeeRepository,
                                DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {

        DashboardStatsDTO dto = new DashboardStatsDTO();

        // ================= BASIC STATS (FROM DB) =================
        long totalEmployees = employeeRepository.count();
        long departments = departmentRepository.count();

        dto.setTotalEmployees(totalEmployees);
        dto.setDepartments(departments);

        // ================= ATTENDANCE RATE (TEMP LOGIC) =================
        // Later: calculate from Attendance table
        dto.setAttendanceRate(95.0);

        // ================= PAYROLL (TEMP LOGIC) =================
        // Later: replace with SUM query from Salary table
        dto.setPayroll(totalEmployees * 70000);

        // ================= CHART DATA (TEMP STATIC - LATER DB) =================
        dto.setJoined(Arrays.asList(5, 8, 6, 10, 7, 12, 9));
        dto.setLeft(Arrays.asList(1, 2, 1, 3, 2, 2, 1));
        dto.setAttendanceTrend(Arrays.asList(92, 95, 94, 96, 93, 97, 98));

        return dto;
    }


    @Override
    public List<Employee> getUpcomingBirthdays() {
        return employeeRepository.findAll()
                .stream()
                .filter(e -> e.getDateOfBirth() != null)
                .limit(5)
                .toList();
    }

    @Override
    public List<Employee> getUpcomingAnniversaries() {
        return employeeRepository.findAll()
                .stream()
                .filter(e -> e.getJoiningDate() != null)
                .limit(5)
                .toList();
    }

    @Override
    public Page<Employee> getUpcomingBirthdays(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> getUpcomingAnniversaries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

}