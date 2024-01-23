package com.example.jop_project.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

  public String home() {
    return "index";
  }
}