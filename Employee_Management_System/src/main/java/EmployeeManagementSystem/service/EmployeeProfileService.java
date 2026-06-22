package EmployeeManagementSystem.service;

import EmployeeManagementSystem.entity.EmployeeProfile;

public interface EmployeeProfileService {
    EmployeeProfile getProfileByUserId(String userId);
    void saveOrUpdateProfile(EmployeeProfile profile,String userId);
}
