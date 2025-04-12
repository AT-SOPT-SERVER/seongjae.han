package org.sopt.configs;

import org.sopt.repository.PostFileRepository;
import org.sopt.repository.PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  @Bean
  public PostRepository postRepository() {
    return new PostFileRepository();
  }
}
