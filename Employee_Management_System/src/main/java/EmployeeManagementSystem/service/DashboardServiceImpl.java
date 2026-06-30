package EmployeeManagementSystem.service;


import EmployeeManagementSystem.dto.DashboardStatsDTO;
import EmployeeManagementSystem.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeService employeeService;
    private final DepartmentRepository departmentRepository;

    public DashboardServiceImpl(EmployeeService employeeService,
                                DepartmentRepository departmentRepository) {
        this.employeeService = employeeService;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {

        DashboardStatsDTO dto = new DashboardStatsDTO();

        dto.setTotalEmployees(employeeService.totalEmployees());
        dto.setDepartments(departmentRepository.count());

        dto.setAttendanceRate(95.0);
        dto.setPayroll(8.5);

        dto.setJoined(Arrays.asList(5, 8, 6, 10, 7, 12, 9));
        dto.setLeft(Arrays.asList(1, 2, 1, 3, 2, 2, 1));
        dto.setAttendanceTrend(Arrays.asList(92, 95, 94, 96, 93, 97, 98));

        return dto;
    }
}