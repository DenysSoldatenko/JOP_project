package com.example.project.configurations;

import static java.nio.file.Paths.get;

import java.nio.file.Path;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to handle static resource mapping for uploaded photos.
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

  private static final String UPLOAD_DIRECTORY = "photos";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    Path uploadDir = get(UPLOAD_DIRECTORY);
    String uploadDirPath = uploadDir.toAbsolutePath().toUri().toString();

    registry.addResourceHandler("/" + UPLOAD_DIRECTORY + "/**")
        .addResourceLocations(uploadDirPath);
  }
}
