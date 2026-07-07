package EmployeeManagementSystem.service;

import EmployeeManagementSystem.entity.Employee;
import EmployeeManagementSystem.entity.EmployeeProfile;
import EmployeeManagementSystem.repository.EmployeeProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

    @Override
    public List<EmployeeProfile> getAllProfiles() {
        return repository.findAll();
    }

    public String uploadPhoto(MultipartFile file, String employeeId) throws IOException {
        // 1. Check if employee exists
        EmployeeProfile employee = repository.findByUserId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));

        // 2. Directory setting
        String uploadDir = "C:/New folder/uploadFile/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 3. Unique filename banana (e.g., BT0001.jpg)
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = employeeId + fileExtension;

        // 4. File ko Physical Folder (C:/New folder/uploadFile/) me save karna
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 5. CRITICAL FIX: Database me file ka naam set karke SAVE karna
        employee.setPhoto(fileName);
        repository.save(employee); // Yeh line missing thi aapke code me

        // Controller ko file ka naya naam return karein
        return fileName;
    }
}
