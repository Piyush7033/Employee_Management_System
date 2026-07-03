package EmployeeManagementSystem.service;

import EmployeeManagementSystem.dto.SalarySlipDto;
import EmployeeManagementSystem.entity.Salary;
import EmployeeManagementSystem.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Override
    public Salary saveSalary(Salary salary) {

        if (salary.getEmployee() != null &&
                salary.getEmployee().getId() != null) {

            Long empId = salary.getEmployee().getId();

            Optional<Salary> existing =
                    salaryRepository.findByEmployee_Id(empId);


            if (existing.isPresent()) {

                Salary s = existing.get();

                s.setBasicSalary(salary.getBasicSalary());
                s.setBonus(salary.getBonus());
                s.setDeduction(salary.getDeduction());

                return salaryRepository.save(s);
            }
        }
        return salaryRepository.save(salary);
    }

    @Override
    public Salary getSalaryById(Long id) {
        return salaryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }

    @Override
    public Double calculateNetSalary(Long id) {

        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary not found"));

        double basic = salary.getBasicSalary() != null ? salary.getBasicSalary() : 0;
        double bonus = salary.getBonus() != null ? salary.getBonus() : 0;
        double deduction = salary.getDeduction() != null ? salary.getDeduction() : 0;

        return basic + bonus - deduction;
    }

    @Override
    public Salary getSalaryByEmployeeId(Long employeeId) {
        return salaryRepository.findByEmployee_Id(employeeId)
                .orElse(null);
    }

    @Override
    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }
    public SalarySlipDto getSalarySlipById(Long id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary record not found"));

        SalarySlipDto dto = new SalarySlipDto();

        if (salary.getEmployee() != null) {
            dto.setEmployeeName(salary.getEmployee().getName());
            dto.setEmployeeId("EMP-" + salary.getEmployee().getId());
        }

        dto.setBasicSalary(salary.getBasicSalary());
        dto.setBonus(salary.getBonus());
        dto.setDeduction(salary.getDeduction());

        dto.setNetSalary(salary.getNetSalary());

        java.time.LocalDate today = java.time.LocalDate.now();
        dto.setMonthYear(today.getMonth().name() + " " + today.getYear());

        return dto;
    }
}