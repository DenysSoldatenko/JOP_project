package com.example.project.controllers;

import static com.example.project.utils.FileDownloadHelper.downloadFile;

import com.example.project.entities.JobSeeker;
import com.example.project.entities.User;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.JobSeekerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  /**
   * Handles the submission of a new job seeker profile.
   *
   * @param jobSeekerProfile The job seeker profile object to be added.
   * @param model            The model to be populated with necessary attributes.
   * @param image            The profile image file to be uploaded.
   * @param pdf              The resume PDF file to be uploaded.
   * @return Redirects to the dashboard page after adding the job seeker profile.
   */
  @PostMapping("/addNew")
  public String addJobSeekerProfile(JobSeeker jobSeekerProfile, Model model,
                                    @RequestParam("image") MultipartFile image,
                                    @RequestParam("pdf") MultipartFile pdf) {
    User currentUser = securityContextHelper.getCurrentUser();
    JobSeeker jobSeeker = jobSeekerService.findById(currentUser.getId());
    model.addAttribute("skills", jobSeeker.getSkills());
    model.addAttribute("profile", jobSeeker);
    jobSeekerService.createJobSeeker(currentUser, jobSeekerProfile, image, pdf);
    return "redirect:/dashboard";
  }

  /**
   * Handles requests to view a specific job seeker's profile by ID.
   *
   * @param id    The ID of the job seeker profile to be viewed.
   * @param model The model to be populated with the job seeker profile.
   * @return The name of the view to render, which is "job-seeker-profile".
   */
  @GetMapping("/{id}")
  public String showProfile(@PathVariable("id") int id, Model model) {
    JobSeeker jobSeeker = jobSeekerService.findById(id);
    model.addAttribute("profile", jobSeeker);
    return "job-seeker-profile";
  }

  /**
   * Handles requests to download a user's resume.
   *
   * @param fileName The name of the file to be downloaded.
   * @param userId   The ID of the user.
   * @return A {@link ResponseEntity} containing the file resource or an error message.
   */
  @GetMapping("/downloadResume")
  public ResponseEntity<?> downloadResume(@RequestParam(value = "fileName") String fileName,
                                          @RequestParam(value = "userID") String userId) {
    return downloadFile(userId, fileName);
  }
}