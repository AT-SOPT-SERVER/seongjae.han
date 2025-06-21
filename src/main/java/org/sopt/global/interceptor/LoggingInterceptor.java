package org.sopt.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.global.util.log.LoggingUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

  private final LoggingUtil loggingUtil;

  public LoggingInterceptor(final LoggingUtil loggingUtil) {
    this.loggingUtil = loggingUtil;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler) throws Exception {
    loggingUtil.info("[LOG] " + request.getMethod() + " " + request.getRequestURI());
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    loggingUtil.info("[LOG] Response Status: " + response.getStatus());
  }
}
