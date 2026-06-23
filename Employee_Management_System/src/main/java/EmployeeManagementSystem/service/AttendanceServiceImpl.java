package EmployeeManagementSystem.service;


import EmployeeManagementSystem.entity.Attendance;
import EmployeeManagementSystem.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    //just for trail
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Attendance saveAttendance(Attendance attendance) {

        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAllAttendance() {

        return attendanceRepository.findAll();
    }

    @Override
    public Attendance getAttendanceById(Long id) {

        return attendanceRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAttendance(Long id) {

        attendanceRepository.deleteById(id);
    }
}