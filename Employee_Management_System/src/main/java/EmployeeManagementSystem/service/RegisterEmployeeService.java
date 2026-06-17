package EmployeeManagementSystem.service;

import EmployeeManagementSystem.dto.LoginRequest;
import EmployeeManagementSystem.entity.RegisterEmployee;

public interface RegisterEmployeeService {
    String registerEmployee(RegisterEmployee employee);
    String login(LoginRequest request);
    String generateNextEmployeeId();
    void sendOtpProcessing(String email);
    void verifyOtpAndChangePassword(String email,String otp,String newPassword);
}
