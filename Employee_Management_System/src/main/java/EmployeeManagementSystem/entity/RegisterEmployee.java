package EmployeeManagementSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RegisterEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;
    private String otp;
    private LocalDateTime otpExpiryTime;

}
