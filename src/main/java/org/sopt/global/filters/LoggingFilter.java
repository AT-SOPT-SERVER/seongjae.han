package org.sopt.global.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.sopt.global.util.log.LoggingUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

  private final LoggingUtil loggingUtil;

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain) throws ServletException, IOException {
    long start = System.currentTimeMillis();
    filterChain.doFilter(request, response);
    long duration = System.currentTimeMillis() - start;

    loggingUtil.info("[LOG] %s %s %dms%n", request.getMethod(), request.getRequestURI(), duration);
  }
}
