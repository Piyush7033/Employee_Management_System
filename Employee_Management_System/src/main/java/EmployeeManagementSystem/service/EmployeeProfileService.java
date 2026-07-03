package EmployeeManagementSystem.service;

import EmployeeManagementSystem.entity.EmployeeProfile;
import EmployeeManagementSystem.repository.EmployeeProfileRepository;

import java.util.List;

public interface EmployeeProfileService {
//    public static List<EmployeeProfile> getAllProfiles() {
//        return EmployeeProfileRepository.findAll();   // ✅ REAL DATA FROM DB
//    }

    List<EmployeeProfile> getAllProfiles();

    EmployeeProfile getProfileByUserId(String userId);
    void saveOrUpdateProfile(EmployeeProfile profile,String userId);
}
