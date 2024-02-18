package com.example.project.utils;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.exists;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.StringUtils.cleanPath;

import com.example.project.entities.Recruiter;
import com.example.project.entities.User;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * Helper class for storing profile photos for recruiters.
 */
@Slf4j
public class FileStorageHelper {

  /**
   * Stores the profile photo for a recruiter.
   *
   * @param user The {@link User} associated with the recruiter.
   * @param recruiter The {@link Recruiter} entity for which the profile photo is stored.
   * @param file The {@link MultipartFile} containing the profile photo.
   */
  @SneakyThrows
  public static void storeProfilePhoto(User user, Recruiter recruiter, MultipartFile file) {
    if (file.isEmpty()) {
      log.warn("Attempted to store an empty file for user: {}", user.getId());
      return;
    }

    String filename = cleanPath(requireNonNull(file.getOriginalFilename()));
    recruiter.setProfilePhoto(filename);

    String uploadDir = "photos/recruiter/" + user.getRecruiter().getRecruiterId();
    saveFile(uploadDir, filename, file);
  }

  /**
   * Saves a file to the specified upload directory.
   *
   * @param uploadDir The directory where the file will be uploaded.
   * @param filename The name of the file to be saved.
   * @param multipartFile The {@link MultipartFile} containing the file data.
   * @throws IOException If an error occurs while saving the file.
   */
  public static void saveFile(String uploadDir,
                              String filename,
                              MultipartFile multipartFile) throws IOException {
    Path uploadPath = get(uploadDir);
    if (!exists(uploadPath)) {
      createDirectories(uploadPath);
      log.info("Created directory: {}", uploadPath);
    }

    try (InputStream inputStream = multipartFile.getInputStream()) {
      Path filePath = uploadPath.resolve(filename);
      log.info("Saving file: {}", filePath);
      copy(inputStream, filePath, REPLACE_EXISTING);
      log.info("File saved successfully: {}", filename);
    } catch (IOException ioe) {
      log.error("Could not save file: {}", filename, ioe);
      throw new IOException("Could not save file: " + filename, ioe);
    }
  }
}
