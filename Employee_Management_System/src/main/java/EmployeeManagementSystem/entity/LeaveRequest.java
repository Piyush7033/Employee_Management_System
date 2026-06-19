package EmployeeManagementSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String employeeId;
    private String employeeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;
    private String reason;
    private String status="PENDING";

}
