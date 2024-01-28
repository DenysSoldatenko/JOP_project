package com.example.jop_project.controllers;

import com.example.jop_project.entities.User;
import com.example.jop_project.entities.UserType;
import com.example.jop_project.services.UserTypeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

  private final UserTypeService usersTypeService;

  @GetMapping("/register")
  public String register(Model model) {
    List<UserType> usersTypes = usersTypeService.getAllUserTypes();
    model.addAttribute("getAllTypes", usersTypes);
    model.addAttribute("user", new User());
    return "register";
  }
}