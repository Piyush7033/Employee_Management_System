package EmployeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double basicSalary;
    private Double bonus;
    private Double deduction;

    // IMPORTANT: OneToOne mapping
    @OneToOne
    @JoinColumn(name = "employee_id", unique = true, nullable = false)
    private Employee employee;

    // Net Salary
    public Double getNetSalary() {
        double basic = basicSalary == null ? 0 : basicSalary;
        double bonusAmt = bonus == null ? 0 : bonus;
        double deductionAmt = deduction == null ? 0 : deduction;

        return basic + bonusAmt - deductionAmt;
    }
}