package EmployeeManagementSystem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, unique = true, nullable = false)
    private String token;

    private String username;

    private boolean revoked;

    private LocalDateTime expiryTime;

}
