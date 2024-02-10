package com.example.project.utils;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.StringUtils.cleanPath;

import com.example.project.entities.Recruiter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class FileStorageHelper {

  @SneakyThrows
  public static void storeProfilePhoto(Recruiter recruiter, MultipartFile file) {
    if (file.isEmpty()) {
      return;
    }

    String filename = cleanPath(requireNonNull(file.getOriginalFilename()));
    recruiter.setProfilePhoto(filename);

    String uploadDir = "photos/recruiter/" + recruiter.getRecruiterId();
    saveFile(uploadDir, filename, file);
  }

  public static void saveFile(String uploadDir,
                              String filename,
                              MultipartFile multipartFile) throws IOException {
    Path uploadPath = Paths.get(uploadDir);
    if (!Files.exists(uploadPath)) {
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
