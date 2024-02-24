package com.example.project.controllers;

import org.springframework.stereotype.Controller;

/**
 * Controller class to handle requests to the home page.
 */
@Controller
public class HomeController {

  /**
   * Handles requests to the home page.
   *
   * @return The name of the view to render, which is "index".
   */
  public String home() {
    return "index";
  }
}