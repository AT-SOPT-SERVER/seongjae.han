package org.sopt.global.config;

import org.sopt.global.util.log.LoggingUtil;
import org.sopt.global.util.log.SystemPrintLoggingUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

  @Bean
  public LoggingUtil loggingUtil() {
    return new SystemPrintLoggingUtil();
  }
}
