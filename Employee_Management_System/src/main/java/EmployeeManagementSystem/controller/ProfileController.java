package EmployeeManagementSystem.controller;

import EmployeeManagementSystem.entity.EmployeeProfile;
import EmployeeManagementSystem.service.EmployeeProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final EmployeeProfileService service;

    @GetMapping("/view")
    public String viewProfile(Model model){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String currentEmplId=auth.getName();
        EmployeeProfile profile=service.getProfileByUserId(currentEmplId);
        model.addAttribute("profile",profile);
        return "profile-view";
    }
    @PostMapping("/save")
    public String saveProfile(@ModelAttribute("profile") EmployeeProfile profile){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String currentEmpId=auth.getName();
        service.saveOrUpdateProfile(profile,currentEmpId);
        return "redirect:/profile/view?success=true";
    }
}
