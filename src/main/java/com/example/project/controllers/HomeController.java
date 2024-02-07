package com.example.project.controllers;

import org.springframework.stereotype.Controller;

/**
 * Controller class to handle requests to the home page.
 */
@Controller
public class HomeController {

  public String home() {
    return "index";
  }
}