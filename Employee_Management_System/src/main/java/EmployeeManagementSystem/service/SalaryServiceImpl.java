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
    public SalarySlipDto getSalarySlipById(Long id) {
        // 1. Database se salary entity nikali
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary record not found"));

        // 2. DTO ka object banaya
        SalarySlipDto dto = new SalarySlipDto();

        // 3. Entity se data nikal kar DTO mein set kiya
        if (salary.getEmployee() != null) {
            dto.setEmployeeName(salary.getEmployee().getName()); // Aapki field ke hisab se (e.g., getName() ya getFirstName())
            dto.setEmployeeId("EMP-" + salary.getEmployee().getId());
        }

        dto.setBasicSalary(salary.getBasicSalary());
        dto.setBonus(salary.getBonus());
        dto.setDeduction(salary.getDeduction());

        // Entity ka net salary formula call kar liya
        dto.setNetSalary(salary.getNetSalary());

        // Current Month aur Year automatic set karne ke liye
        java.time.LocalDate today = java.time.LocalDate.now();
        dto.setMonthYear(today.getMonth().name() + " " + today.getYear());

        return dto;
    }
}