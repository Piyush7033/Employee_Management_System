package EmployeeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {

    // =========================
    // Employee Statistics
    // =========================
    private long totalEmployees;
    private long departments;
    private double attendanceRate;

    // =========================
    // Salary Statistics
    // =========================
    private double totalPayrollCost;
    private double paidSalary;
    private double pendingSalary;

    private long paidEmployees;
    private long pendingEmployees;

    // =========================
    // Additional Salary Metrics
    // =========================
    private double averageSalary;
    private double highestSalary;
    private double lowestSalary;

    // =========================
    // Employee Trends
    // =========================
    private List<Integer> joined;
    private List<Integer> left;
    private List<Integer> attendanceTrend;

    // =========================
    // Payroll Charts
    // =========================
    private List<String> departmentNames;
    private List<Double> departmentPayroll;

    // Monthly Payroll Trend
    private List<String> payrollMonths;
    private List<Double> monthlyPayroll;

    // Salary Status Chart
    private List<String> paymentStatusLabels;
    private List<Long> paymentStatusCounts;
}