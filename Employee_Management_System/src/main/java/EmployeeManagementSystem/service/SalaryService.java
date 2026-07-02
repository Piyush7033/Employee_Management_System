package EmployeeManagementSystem.service;

import EmployeeManagementSystem.dto.SalarySlipDto;
import EmployeeManagementSystem.entity.Salary;

import java.util.List;

public interface SalaryService {

    Salary saveSalary(Salary salary);

    Salary getSalaryById(Long id);

    void deleteSalary(Long id);

    Double calculateNetSalary(Long id);

    Salary getSalaryByEmployeeId(Long employeeId);

    List<Salary> getAllSalaries();
    SalarySlipDto getSalarySlipById(Long id);
}