package EmployeeManagementSystem.service;

import EmployeeManagementSystem.entity.EmployeeProfile;
import EmployeeManagementSystem.repository.EmployeeProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeProfileServiceImpl implements EmployeeProfileService{
    private final EmployeeProfileRepository repository;

    public EmployeeProfile getProfileByUserId(String userId){
    return repository.findByUserId(userId).orElse(new EmployeeProfile());
    }
    public void saveOrUpdateProfile(EmployeeProfile profile,String userId){
    EmployeeProfile existingProfile=repository.findByUserId(userId).orElse(null);
    if (existingProfile!=null){
        profile.setId(existingProfile.getId());
    }
        profile.setUserId(userId);
    repository.save(profile);
    }
}
