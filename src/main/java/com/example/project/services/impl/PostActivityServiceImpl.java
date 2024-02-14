package com.example.project.services.impl;

import com.example.project.entities.PostActivity;
import com.example.project.entities.User;
import com.example.project.repositories.PostActivityRepository;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.PostActivityService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link PostActivityService} interface.
 * Provides methods to manage post activities.
 */
@Service
@RequiredArgsConstructor
public class PostActivityServiceImpl implements PostActivityService {

  private final PostActivityRepository postActivityRepository;
  private final SecurityContextHelper securityContextHelper;

  @Override
  public void createPostActivity(PostActivity postActivity) {
    User user = securityContextHelper.getCurrentUser();
    postActivity.setUser(user);
    postActivity.setPostedDate(new Date());
    postActivityRepository.save(postActivity);
  }
}
