package EmployeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String departmentName;

    private String description;

    // One Department -> Many Employees
    @OneToMany(
            mappedBy = "department",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Employee> employees = new ArrayList<>();

    // Helper Method
    public void addEmployee(Employee employee) {

        employees.add(employee);
        employee.setDepartment(this);
    }

    // Helper Method
    public void removeEmployee(Employee employee) {

        employees.remove(employee);
        employee.setDepartment(null);
    }

}
