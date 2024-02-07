package com.example.project.controllers;

import static com.example.project.utils.ErrorMessages.USER_NOT_FOUND;

import com.example.project.entities.Recruiter;
import com.example.project.entities.User;
import com.example.project.repositories.UserRepository;
import com.example.project.services.RecruiterService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recruiter-profile")
public class RecruiterController {

  private final UserRepository usersRepository;
  private final RecruiterService recruiterProfileService;

  @GetMapping("/")
  public String recruiterProfile(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      User user = usersRepository.findByEmail(currentUsername)
          .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
      Optional<Recruiter> recruiter = recruiterProfileService.findById(user.getId());
      recruiter.ifPresent(r -> model.addAttribute("profile", recruiter));
    }

    return "recruiter_profile";
  }
}