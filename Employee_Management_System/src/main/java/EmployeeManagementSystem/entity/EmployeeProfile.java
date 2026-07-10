package EmployeeManagementSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class EmployeeProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String fullName;
    private String phoneNumber;
    private String dob;
    private String gender;
    private String bloodGroup;
    private String email;
    private String maritalStatus;
    private String fieldOfStudy;
    private String highestQualification;
    private String university;
    private String passingYear;
    private String bankName;
    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;
    private String currentAddress;
    private String permanentAddress;
    private String photo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
