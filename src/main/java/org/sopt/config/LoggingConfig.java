package org.sopt.config;

import org.sopt.util.LoggingUtil;
import org.sopt.util.SystemPrintLoggingUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

  @Bean
  public LoggingUtil loggingUtil() {
    return new SystemPrintLoggingUtil();
  }
}
