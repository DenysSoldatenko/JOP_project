package com.example.project.controllers;

import static com.example.project.utils.FileStorageHelper.storeJobSeekerPhoto;

import com.example.project.entities.JobSeeker;
import com.example.project.entities.User;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.JobSeekerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller class for handling job seeker profile-related requests.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/job-seeker-profile")
public class JobSeekerController {

  private final JobSeekerService jobSeekerService;
  private final SecurityContextHelper securityContextHelper;

  /**
   * Handles requests to view the job seeker's profile.
   *
   * @param model The {@link Model} object used to pass attributes to the view.
   * @return The name of the view to render, which is "job-seeker-profile".
   */
  @GetMapping("/")
  public String showJobSeekerProfile(Model model) {
    User currentUser = securityContextHelper.getCurrentUser();
    JobSeeker jobSeeker = jobSeekerService.findById(currentUser.getId());
    model.addAttribute("skills", jobSeeker.getSkills());
    model.addAttribute("profile", jobSeeker);
    return "job-seeker-profile";
  }

  @PostMapping("/addNew")
  public String addJobSeekerProfile(JobSeeker jobSeekerProfile, Model model,
                                    @RequestParam("image") MultipartFile image,
                                    @RequestParam("pdf") MultipartFile pdf) {
    User currentUser = securityContextHelper.getCurrentUser();
    JobSeeker jobSeeker = jobSeekerService.findById(currentUser.getId());
    model.addAttribute("skills", jobSeeker.getSkills());
    model.addAttribute("profile", jobSeeker);
    storeJobSeekerPhoto(currentUser, jobSeekerProfile, image, pdf);
    jobSeekerService.createJobSeeker(currentUser, jobSeekerProfile);
    return "redirect:/dashboard";
  }
}
