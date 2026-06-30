package EmployeeManagementSystem.dto;

import java.util.List;

public class DashboardStatsDTO {

    private long totalEmployees;
    private long departments;
    private double attendanceRate;
    private double payroll;

    private List<Integer> joined;
    private List<Integer> left;
    private List<Integer> attendanceTrend;

    // getters & setters

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getDepartments() {
        return departments;
    }

    public void setDepartments(long departments) {
        this.departments = departments;
    }

    public double getAttendanceRate() {
        return attendanceRate;
    }

    public void setAttendanceRate(double attendanceRate) {
        this.attendanceRate = attendanceRate;
    }

    public double getPayroll() {
        return payroll;
    }

    public void setPayroll(double payroll) {
        this.payroll = payroll;
    }

    public List<Integer> getJoined() {
        return joined;
    }

    public void setJoined(List<Integer> joined) {
        this.joined = joined;
    }

    public List<Integer> getLeft() {
        return left;
    }

    public void setLeft(List<Integer> left) {
        this.left = left;
    }

    public List<Integer> getAttendanceTrend() {
        return attendanceTrend;
    }

    public void setAttendanceTrend(List<Integer> attendanceTrend) {
        this.attendanceTrend = attendanceTrend;
    }
}