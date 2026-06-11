package EmployeeManagementSystem.service;

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

    // =========================
    // SAVE OR UPDATE (FIXED)
    // =========================
    @Override
    public Salary saveSalary(Salary salary) {

        if (salary.getEmployee() != null &&
                salary.getEmployee().getId() != null) {

            Long empId = salary.getEmployee().getId();

            Optional<Salary> existing =
                    salaryRepository.findByEmployee_Id(empId);

            // ✅ UPDATE FLOW
            if (existing.isPresent()) {

                Salary s = existing.get();

                s.setBasicSalary(salary.getBasicSalary());
                s.setBonus(salary.getBonus());
                s.setDeduction(salary.getDeduction());

                return salaryRepository.save(s);
            }
        }

        // ✅ INSERT FLOW
        return salaryRepository.save(salary);
    }

    // =========================
    // GET BY ID
    // =========================
    @Override
    public Salary getSalaryById(Long id) {
        return salaryRepository.findById(id).orElse(null);
    }

    // =========================
    // DELETE
    // =========================
    @Override
    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }

    // =========================
    // NET SALARY
    // =========================
    @Override
    public Double calculateNetSalary(Long id) {

        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary not found"));

        double basic = salary.getBasicSalary() != null ? salary.getBasicSalary() : 0;
        double bonus = salary.getBonus() != null ? salary.getBonus() : 0;
        double deduction = salary.getDeduction() != null ? salary.getDeduction() : 0;

        return basic + bonus - deduction;
    }

    // =========================
    // GET BY EMPLOYEE ID
    // =========================
    @Override
    public Salary getSalaryByEmployeeId(Long employeeId) {
        return salaryRepository.findByEmployee_Id(employeeId)
                .orElse(null);
    }

    // =========================
    // GET ALL
    // =========================
    @Override
    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }
}