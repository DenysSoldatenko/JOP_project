package com.example.project.utils;

import static com.example.project.utils.ErrorMessages.FILE_NOT_FOUND;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.list;
import static java.nio.file.Paths.get;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.parseMediaType;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;

/**
 * Helper class for handling file download operations.
 */
@Slf4j
public class FileDownloadHelper {

  /**
   * Downloads a file for a given user and file name.
   *
   * @param userId   The ID of the user.
   * @param fileName The name of the file to be downloaded.
   * @return A {@link ResponseEntity} containing the file resource or an error message.
   */
  @SneakyThrows
  public static ResponseEntity<?> downloadFile(String userId, String fileName) {
    log.info("Downloading file: {}/{}", userId, fileName);
    Resource resource = loadFile("photos/candidate/" + userId, fileName);

    if (resource == null) {
      return new ResponseEntity<>(FILE_NOT_FOUND + fileName, NOT_FOUND);
    }

    String contentType = "application/octet-stream";
    String encodedFileName = encodeFileName(resource.getFilename());
    String headerValue = "attachment; filename*=UTF-8''" + encodedFileName;

    return ResponseEntity.ok()
      .contentType(parseMediaType(contentType))
      .header(CONTENT_DISPOSITION, headerValue)
      .body(resource);
  }


  private static Resource loadFile(String dir, String name) {
    log.info("Loading file from directory: {}, with name starting with: {}", dir, name);
    Path dirPath = get(dir);

    try (Stream<Path> files = list(dirPath)) {
      Path targetFile = files
          .filter(file -> file.getFileName().toString().startsWith(name))
          .findFirst()
          .orElse(null);

      if (targetFile != null) {
        log.info("File found: {}", targetFile);
        return new UrlResource(targetFile.toUri());
      } else {
        log.error("File not found in directory: {}", dir);
        return null;
      }
    } catch (IOException e) {
      log.error("Error loading file from directory: {}", dir, e);
      return null;
    }
  }

  private static String encodeFileName(String fileName) {
    return encode(fileName, UTF_8).replaceAll("\\+", "%20");
  }
}
