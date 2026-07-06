package EmployeeManagementSystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DashboardStatsDTO {


    private long totalEmployees;
    private long departments;
    private double attendanceRate;
    private double payroll;


    private List<Integer> joined;
    private List<Integer> left;
    private List<Integer> attendanceTrend;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(long totalEmployees, long departments,
                             double attendanceRate, double payroll,
                             List<Integer> joined,
                             List<Integer> left,
                             List<Integer> attendanceTrend) {
        this.totalEmployees = totalEmployees;
        this.departments = departments;
        this.attendanceRate = attendanceRate;
        this.payroll = payroll;
        this.joined = joined;
        this.left = left;
        this.attendanceTrend = attendanceTrend;
    }


}