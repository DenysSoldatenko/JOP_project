package com.example.jop_project.controllers;

import static com.example.jop_project.utils.ErrorMessages.EMAIL_ALREADY_REGISTERED;

import com.example.jop_project.entities.User;
import com.example.jop_project.entities.UserType;
import com.example.jop_project.services.UserService;
import com.example.jop_project.services.UserTypeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller class for handling user-related operations.
 */
@Controller
@RequiredArgsConstructor
public class UserController {

  private final UserTypeService userTypeService;
  private final UserService userService;

  /**
   * Displays the user registration form.
   *
   * @param model The model to be populated with data for the view.
   * @return The name of the view template to render, "register".
   */
  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    List<UserType> userTypes = userTypeService.getAllUserTypes();
    model.addAttribute("userTypes", userTypes);
    model.addAttribute("newUser", new User());
    return "register";
  }

  /**
   * Registers a new user.
   *
   * @param user The {@link User} object representing the new user to be registered.
   * @param model The model to be populated with data for the view.
   * @return The name of the view template to render based on the outcome:
   *         - "register" if registration fails (due to email already registered).
   *         - "dashboard" if registration is successful.
   */
  @PostMapping("/register/new")
  public String registerUser(@Valid User user, Model model) {
    Optional<User> existingUser = userService.getUserByEmail(user.getEmail());

    if (existingUser.isPresent()) {
      model.addAttribute("error", EMAIL_ALREADY_REGISTERED);
      List<UserType> userTypes = userTypeService.getAllUserTypes();
      model.addAttribute("userTypes", userTypes);
      model.addAttribute("newUser", new User());
      return "register";
    }

    userService.createUser(user);
    return "dashboard";
  }

}