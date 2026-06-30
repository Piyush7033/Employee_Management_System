package EmployeeManagementSystem.service;

import EmployeeManagementSystem.dto.DashboardStatsDTO;
import EmployeeManagementSystem.entity.Employee;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;

public interface DashboardService {
    DashboardStatsDTO getDashboardStats();

    @Nullable Object getUpcomingBirthdays();

    @Nullable Object getUpcomingAnniversaries();


    Page<Employee> getUpcomingBirthdays(int page, int size);

    Page<Employee> getUpcomingAnniversaries(int page, int size);
}
